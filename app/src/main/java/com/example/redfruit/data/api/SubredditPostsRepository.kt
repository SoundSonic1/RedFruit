package com.example.redfruit.data.api

import android.util.Log
import com.example.redfruit.data.model.Gildings
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.data.model.SubredditListing
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.model.images.ImageSource
import com.example.redfruit.data.model.images.RedditImage
import com.example.redfruit.data.model.media.RedditVideo
import com.example.redfruit.data.model.media.SecureMedia
import com.example.redfruit.data.model.media.YoutubeoEmbed
import com.example.redfruit.util.Constants
import com.example.redfruit.util.getResponse
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Implements the Repository pattern
 * @property subredditMap collects the subreddit
 */
open class SubredditPostsRepository(
    private val subredditMap: MutableMap<Pair<String, SortBy>, SubredditListing> = mutableMapOf()
) : IPostsRepository<List<Post>> {

    /**
     * Main function that returns a list of posts from a given subreddit
     * for ViewModels
     *
     * @param sub name of the subreddit
     * @param sortBy how the posts should be sorted
     * @param limit the amount of the posts that are to be fetched
     * @return a list of reddit posts
     */
    override fun getData(sub: String, sortBy: SortBy, limit: Int): List<Post> {
        // TODO: check if sub is valid
        val subReddit = subredditMap.getOrPut(Pair(sub, sortBy)) { SubredditListing(sub) }
        var redditUrl = "${Constants.BASE_URL}${subReddit.name}/${sortBy.name}.json?limit=$limit&raw_json=1"
        if (subReddit.after.isNotBlank()) {
            redditUrl = "$redditUrl&after=${subReddit.after}"
        }
        // For debug purposes
        Log.d("repo", redditUrl)
        val response = getResponse(redditUrl)
        if (response.isBlank()) {
            return listOf()
        }

        val jsonObjResponse = JsonParser().parse(response).asJsonObject
        if (jsonObjResponse.get("kind")?.asString != "Listing") {
            // either private or banned sub
            return listOf()
        }
        val jsonData = jsonObjResponse.getAsJsonObject("data")
        // JSONArray of children aka posts
        val jsonChildren = jsonData.getAsJsonArray("children")

        // check if children are posts
        if (jsonChildren.any { it.asJsonObject.get("kind").asString != "t3" }) {
            return listOf()
        }

        // bookmark beginning and ending of the listing
        if (!jsonData.get("before").isJsonNull) {
            subReddit.before = jsonData.get("before").asString
        }

        if (!jsonData.get("after").isJsonNull) {
            subReddit.after = jsonData.get("after").asString
        } else if (jsonData.get("after").isJsonNull && subReddit.children.size > 0) {
            return subReddit.children.toList()
        }

        val gson = GsonBuilder()
            .registerTypeAdapter(Post::class.java, PostDeserializer())
            .create()

        subReddit.children.addAll(
            gson.fromJson(jsonChildren, object: TypeToken<List<Post>>() {}.type))

        return subReddit.children.toList()
    }

    /**
     * Clear all data to make a clean refresh
     */
    override fun clearData() = subredditMap.clear()


    protected class PostDeserializer : JsonDeserializer<Post> {
        /**
         * Used to deserialize a json array
         */
        override fun deserialize(json: JsonElement?,
                                 typeOfT: Type?,
                                 context: JsonDeserializationContext?
        ): Post {
            // Throw NPE here if there is no JsonObject data
            val jsonData = json!!.asJsonObject.getAsJsonObject("data")
            val jsonPreview = jsonData.getAsJsonObject("preview")
            val enabled = jsonPreview?.get("enabled")?.asBoolean ?: false
            val jsonArrayImages = jsonPreview?.getAsJsonArray("images")

            val gson = Gson()

            val images = jsonArrayImages?.map {
                RedditImage(
                    source = gson.fromJson(it.asJsonObject.get("source"), ImageSource::class.java),
                    resolutions = gson.fromJson(it.asJsonObject.get("resolutions"), object : TypeToken<List<ImageSource>>() {}.type)
                )
            }

            val secureMedia: SecureMedia? = if (jsonData.get("secure_media").isJsonNull) {
                null
            } else {
                getSecureMedia(jsonData.getAsJsonObject("secure_media"))
            }


            return Post(
                id = jsonData.get("id").asString,
                title = jsonData.get("title").asString,
                author = jsonData.get("author")?.asString ?: "Unknown",
                score = jsonData.get("score")?.asString ?: "0",
                num_comments = jsonData.get("num_comments")?.asString ?: "0",
                post_hint = jsonData.get("post_hint")?.asString ?: "",
                preview = Preview(
                    enabled = enabled,
                    images = images ?: listOf()
                ),
                secureMedia = secureMedia,
                gildings = gson.fromJson(jsonData.get("gildings"), Gildings::class.java),
                over_18 = jsonData.get("over_18")?.asBoolean ?: false,
                stickied = jsonData.get("stickied")?.asBoolean ?: false,
                selftext = jsonData.get("selftext")?.asString ?: "",
                url = jsonData.get("url")?.asString ?: ""
            )
        }

        private fun getSecureMedia(jsonObj: JsonObject): SecureMedia {
            val redditVideo = jsonObj.get("reddit_video")?.let {
                Gson().fromJson(it.asJsonObject, RedditVideo::class.java)
            }

            val youtubeOembed = if (jsonObj.get("type")?.asString == "youtube.com") {
                jsonObj.get("oembed")?.let {
                    Gson().fromJson(it.asJsonObject, YoutubeoEmbed::class.java)
                }
            } else {
                null
            }

            return SecureMedia(redditVideo, youtubeOembed)
        }

    }
}