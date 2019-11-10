package com.example.redfruit.data.adapter

import com.example.redfruit.data.model.Gildings
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.data.model.images.ImageSource
import com.example.redfruit.data.model.images.RedditImage
import com.example.redfruit.data.model.media.RedditVideo
import com.example.redfruit.data.model.media.SecureMedia
import com.example.redfruit.data.model.media.YoutubeoEmbed
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class PostAdapter @Inject constructor() {
    private val moshi = Moshi.Builder().build()

    private val mapAdapter = moshi.adapter(Map::class.java)

    private val imageAdapter = moshi.adapter(ImageSource::class.java)

    private val gildingsAdapter = moshi.adapter(Gildings::class.java)

    private val redditVideoAdapter = moshi.adapter(RedditVideo::class.java)

    private val youtubeOembedAdapter = moshi.adapter(YoutubeoEmbed::class.java)

    @FromJson
    fun fromJson(jsonMap: Map<*, *>): Post {

        val data = jsonMap["data"] as? Map<*, *> ?: throw JsonDataException(
            "PostAdapter.fromJson(): type \"data\" does not exist."
        )

        val preview = data["preview"] as? Map<*, *>
        val enabled = preview?.get("enabled") as? Boolean ?: false

        val imagesList = preview?.get("images") as? List<Map<*, *>>
        val images = imagesList?.map { imageMap ->
            val resolutionsList = imageMap["resolutions"] as List<Map<*, *>>
            val res = resolutionsList.map { value ->
                mapAdapter.toJson(value)
            }
            RedditImage(
                source = imageAdapter.fromJson(mapAdapter.toJson(imageMap["source"] as Map<*, *>))!!,
                resolutions = res.map { imageAdapter.fromJson(it)!! }
            )
        }

        val secureMediaMap = data["secure_media"] as? Map<*, *>
        val secureMedia = secureMediaMap?.let {
            getSecureMedia(it)
        }

        return Post(
            id = data["id"] as? String ?: "",
            title = data["title"] as? String ?: "",
            author = data["author"] as? String ?: "Unknown",
            score = ((data["score"] as? Double)?.toLong() ?: 0).toString(),
            num_comments = ((data["num_comments"] as? Double)?.toLong() ?: 0).toString(),
            post_hint = data["post_hint"] as? String ?: "",
            preview = Preview(
                enabled = enabled,
                images = images ?: listOf()
            ),
            secureMedia = secureMedia,
            gildings = gildingsAdapter.fromJson(mapAdapter.toJson(data["gildings"] as Map<*, *>))!!,
            over_18 = data["over_18"] as? Boolean ?: false,
            stickied = data["stickied"] as? Boolean ?: false,
            selftext = data["selftext"] as? String ?: "",
            subreddit = data["subreddit"] as? String ?: "",
            url = data["url"] as? String ?: ""
        )
    }

    private fun getSecureMedia(jsonMap: Map<*, *>): SecureMedia {

        val redditVideo = jsonMap["reddit_video"]?.let {
            redditVideoAdapter.fromJson(mapAdapter.toJson(it as Map<*, *>))
        }

        val youtubeOembed = if (jsonMap["type"] == "youtube.com") {
            jsonMap["oembed"]?.let {
                youtubeOembedAdapter.fromJson(mapAdapter.toJson(it as Map<*, *>))
            }
        } else {
            null
        }

        return SecureMedia(redditVideo, youtubeOembed)
    }
}
