package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.deserializer.PostDeserializer
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.SubredditListing
import com.example.redfruit.data.model.enumeration.SortBy
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implements the Repository pattern
 * @property subredditMap collects the subreddit
 */
class SubredditPostsRepository(
    private val redditApi: IRedditApi,
    private val subredditMap: MutableMap<Pair<String, SortBy>, SubredditListing> = mutableMapOf()
) : IPostsRepository {

    private val gson = GsonBuilder()
        .registerTypeAdapter(Post::class.java, PostDeserializer()).create()

    /**
     * Main function that returns a list of posts from a given subreddit
     * for ViewModels
     *
     * @param sub name of the subreddit
     * @param sortBy how the posts should be sorted
     * @param limit the amount of the posts that are to be fetched
     * @return a list of reddit posts
     */
    override suspend fun getData(
        sub: String,
        sortBy: SortBy,
        limit: Int
    ): List<Post> = withContext(Dispatchers.Default) {

        val subreddit = subredditMap.getOrPut(Pair(sub, sortBy)) {
            SubredditListing(sub)
        }

        val response = redditApi.getSubredditPosts(sub, sortBy, subreddit.after, limit)
        if (response.isBlank()) {
            return@withContext listOf<Post>()
        }

        val jsonObjResponse = JsonParser().parse(response).asJsonObject
        if (jsonObjResponse.get("kind")?.asString != "Listing") {
            // either private or banned sub
            return@withContext listOf<Post>()
        }
        val jsonData = jsonObjResponse.getAsJsonObject("data")
        // JSONArray of children aka posts
        val jsonChildren = jsonData.getAsJsonArray("children")

        // check if children are posts
        if (jsonChildren.any { it.asJsonObject.get("kind").asString != "t3" }) {
            return@withContext listOf<Post>()
        }

        // bookmark beginning and ending of the listing
        if (!jsonData.get("before").isJsonNull) {
            subreddit.before = jsonData.get("before").asString
        }

        if (!jsonData.get("after").isJsonNull) {
            subreddit.after = jsonData.get("after").asString
        } else if (jsonData.get("after").isJsonNull && subreddit.children.size > 0) {
            return@withContext subreddit.children.toList()
        }

        subreddit.children.addAll(
            gson.fromJson(jsonChildren, object: TypeToken<List<Post>>() {}.type))

        subreddit.children.toList()
    }

    /**
     * Clear all data to make a clean refresh
     */
    override fun clearData() = subredditMap.clear()

}