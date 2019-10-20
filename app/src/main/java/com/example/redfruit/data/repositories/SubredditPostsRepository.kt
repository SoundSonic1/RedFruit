package com.example.redfruit.data.repositories

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.deserializer.PostDeserializer
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.SubredditListing
import com.example.redfruit.data.model.enumeration.SortBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository which manages subreddit posts
 */
class SubredditPostsRepository(
    private val redditApi: IRedditApi,
    private val subredditMap: MutableMap<Pair<String, SortBy>, SubredditListing> = mutableMapOf()
) : IPostsRepository {
    // TODO: inject
    private val deserializer = PostDeserializer(Klaxon())

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

        if (response.isBlank()) return@withContext listOf<Post>()

        val stringBuilder = StringBuilder(response)
        val jsonObj = Parser.default().parse(stringBuilder) as JsonObject
        if (jsonObj.string("kind") != "Listing") {
            // either private or banned sub
            return@withContext listOf<Post>()
        }
        val data = jsonObj.obj("data")!!
        // JSONArray of children aka posts
        val children = data.array<JsonObject>("children")

        // check if children are posts
        if (children!!.any { it.string("kind") != "t3" }) {
            return@withContext listOf<Post>()
        }

        subreddit.before = data.string("before") ?: ""

        if (data.get("after") != null) {
            subreddit.after = data.string("after") ?: ""
        } else if (data.get("after") == null && subreddit.children.size > 0) {
            return@withContext subreddit.children.toList()
        }

        val posts = children.map {
            deserializer.deserialize(it)
        }

        subreddit.children.addAll(posts)

        subreddit.children.toList()
    }

    /**
     * Clear all data to make a clean refresh
     */
    override fun clearData() = subredditMap.clear()

}