package com.example.redfruit.data.deserializer

import com.example.redfruit.data.model.Gildings
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.data.model.images.ImageSource
import com.example.redfruit.data.model.images.RedditImage
import com.example.redfruit.data.model.media.RedditVideo
import com.example.redfruit.data.model.media.SecureMedia
import com.example.redfruit.data.model.media.YoutubeoEmbed
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Custom deserializer for Posts
 */
class PostDeserializer : JsonDeserializer<Post> {

    private val gson = Gson()

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Post {

        val jsonObj = json!!.asJsonObject
        val data = jsonObj.getAsJsonObject("data")
        val preview = data.getAsJsonObject("preview")

        val enabled = preview?.get("enabled")?.asBoolean ?: false
        val jsonImages = preview?.getAsJsonArray("images")

        val images = jsonImages?.map {
            RedditImage(
                source = gson.fromJson(it.asJsonObject.get("source"), ImageSource::class.java),
                resolutions = gson.fromJson(it.asJsonObject.get("resolutions"), object : TypeToken<List<ImageSource>>() {}.type)
            )
        }
        val secureMedia: SecureMedia? = if (data.get("secure_media").isJsonNull) {
            null
        } else {
            getSecureMedia(data.getAsJsonObject("secure_media"))
        }

        return Post(
            id = data.get("id").asString,
            title = data.get("title").asString,
            author = data.get("author")?.asString ?: "Unknown",
            score = data.get("score")?.asString ?: "0",
            num_comments = data.get("num_comments")?.asString ?: "0",
            post_hint = data.get("post_hint")?.asString ?: "",
            preview = Preview(
                enabled = enabled,
                images = images ?: listOf()
            ),
            secureMedia = secureMedia,
            gildings = gson.fromJson(data.get("gildings"), Gildings::class.java),
            over_18 = data.get("over_18")?.asBoolean ?: false,
            stickied = data.get("stickied")?.asBoolean ?: false,
            selftext = data.get("selftext")?.asString ?: "",
            subreddit = data.get("subreddit")?.asString ?: "",
            url = data.get("url")?.asString ?: ""
        )

    }

    private fun getSecureMedia(jsonObj: JsonObject): SecureMedia {
        val redditVideo = jsonObj.get("reddit_video")?.let {
            gson.fromJson(it.asJsonObject, RedditVideo::class.java)
        }

        val youtubeOembed = if (jsonObj.get("type")?.asString == "youtube.com") {
            jsonObj.get("oembed")?.let {
                gson.fromJson(it.asJsonObject, YoutubeoEmbed::class.java)
            }
        } else {
            null
        }

        return SecureMedia(redditVideo, youtubeOembed)
    }
}