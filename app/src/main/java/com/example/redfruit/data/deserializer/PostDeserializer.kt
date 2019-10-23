package com.example.redfruit.data.deserializer

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.redfruit.data.model.Gildings
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.data.model.images.ImageSource
import com.example.redfruit.data.model.images.RedditImage
import com.example.redfruit.data.model.media.RedditVideo
import com.example.redfruit.data.model.media.SecureMedia
import com.example.redfruit.data.model.media.YoutubeoEmbed
import com.example.redfruit.util.IFactory

/**
 * Custom deserializer for json post
 */
class PostDeserializer(
    private val klaxonFactory: IFactory<Klaxon>
) : IDeserializer<Post> {

    override fun deserialize(json: JsonObject): Post {
        val klaxon = klaxonFactory.build()
        val data = json.obj("data")!!
        val preview = data.obj("preview")
        val enabled = preview?.boolean("enabled") ?: false

        val arrayImages = preview?.array<JsonObject>("images")
        val images = arrayImages?.map {
            RedditImage(
                source = klaxon.parse<ImageSource>(it.obj("source")!!.toJsonString())!!,
                resolutions = klaxon.parseArray(it.array<JsonObject>("resolutions")!!.toJsonString()) ?: listOf()
            )
        }

        val secureMedia: SecureMedia? = data.obj("secure_media")?.let {
            getSecureMedia(it, klaxon)
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
            gildings = klaxon.parse<Gildings>(data.obj("gildings")!!.toJsonString())!!,
            over_18 = data.boolean("over18") ?: false,
            stickied = data.boolean("stickied") ?: false,
            selftext = data.string("selftext") ?: "",
            subreddit = data.string("subreddit") ?: "",
            url = data.string("url") ?: ""
        )
    }

    private fun getSecureMedia(jsonObj: JsonObject, klaxon: Klaxon): SecureMedia {

        val redditVideo = jsonObj.obj("reddit_video")?.let {
            klaxon.parse<RedditVideo>(it.toJsonString())
        }

        val youtubeOembed = if (jsonObj.string("type") == "youtube.com") {
            jsonObj.obj("oembed")?.let {
                klaxon.parse<YoutubeoEmbed>(it.toJsonString())
            }
        } else {
            null
        }

        return SecureMedia(redditVideo, youtubeOembed)
    }
}