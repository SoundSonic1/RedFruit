package com.example.redfruit.data.adapter

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.jackson.jackson
import com.example.redfruit.data.model.Gildings
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.data.model.images.ImageSource
import com.example.redfruit.data.model.images.RedditImage
import com.example.redfruit.data.model.media.RedditVideo
import com.example.redfruit.data.model.media.SecureMedia
import com.example.redfruit.data.model.media.YoutubeoEmbed
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class PostAdapter {
    private val moshi = Moshi.Builder().build()

    private val type = Types.newParameterizedType(List::class.java, ImageSource::class.java)

    private val mapAdapter = moshi.adapter(Map::class.java)

    private val imageAdapter = moshi.adapter(ImageSource::class.java)

    private val resolutionsAdapter = moshi.adapter<List<ImageSource>>(type)

    private val gildingsAdapter = moshi.adapter(Gildings::class.java)

    private val redditVideoAdapter = moshi.adapter(RedditVideo::class.java)

    private val youtubeOembedAdapter = moshi.adapter(YoutubeoEmbed::class.java)

    @FromJson
    fun fromJson(jsonMap: Map<*, *>): Post {

        val jsonString = mapAdapter.toJson(jsonMap)
        val jsonStringBuilder = StringBuilder(jsonString)
        val jsonObj = Parser.jackson().parse(jsonStringBuilder) as JsonObject
        val data = jsonObj.obj("data")!!
        val preview = data.obj("preview")
        val enabled = preview?.boolean("enabled") ?: false

        val arrayImages = preview?.array<JsonObject>("images")
        val images = arrayImages?.map {
            RedditImage(
                source = imageAdapter.fromJson(it.obj("source")!!.toJsonString())!!,
                resolutions = resolutionsAdapter.fromJson(it.array<JsonObject>("resolutions")!!.toJsonString()) ?: listOf()
            )
        }

        val secureMedia: SecureMedia? = data.obj("secure_media")?.let {
            getSecureMedia(it)
        }

        return Post(
            id = data.string("id") ?: "",
            title = data.string("title") ?: "",
            author = data.string("author") ?: "Unknown",
            score = (data.int("score") ?: 0).toString(),
            num_comments = (data.int("num_comments") ?: 0).toString(),
            post_hint = data.string("post_hint") ?: "",
            preview = Preview(
                enabled = enabled,
                images = images ?: listOf()
            ),
            secureMedia = secureMedia,
            gildings = gildingsAdapter.fromJson(data.obj("gildings")!!.toJsonString())!!,
            over_18 = data.boolean("over18") ?: false,
            stickied = data.boolean("stickied") ?: false,
            selftext = data.string("selftext") ?: "",
            subreddit = data.string("subreddit") ?: "",
            url = data.string("url") ?: ""
        )
    }

    private fun getSecureMedia(jsonObj: JsonObject): SecureMedia {

        val redditVideo = jsonObj.obj("reddit_video")?.let {
            redditVideoAdapter.fromJson(it.toJsonString())
        }

        val youtubeOembed = if (jsonObj.string("type") == "youtube.com") {
            jsonObj.obj("oembed")?.let {
                youtubeOembedAdapter.fromJson(it.toJsonString())
            }
        } else {
            null
        }

        return SecureMedia(redditVideo, youtubeOembed)
    }
}