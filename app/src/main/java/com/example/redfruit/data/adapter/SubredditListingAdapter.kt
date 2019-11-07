package com.example.redfruit.data.adapter

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.jackson.jackson
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.SubredditListing
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi

class SubredditListingAdapter {

    private val moshi = Moshi.Builder().add(PostAdapter()).build()

    private val mapAdapter = moshi.adapter(Map::class.java)

    private val postAdapter = moshi.adapter(Post::class.java)

    @FromJson
    fun fromJson(jsonMap: Map<*, *>): SubredditListing? {

        val jsonString = mapAdapter.toJson(jsonMap)
        val jsonStringBuilder = StringBuilder(jsonString)
        val jsonObj = Parser.jackson().parse(jsonStringBuilder) as JsonObject

        if (jsonObj.string("kind") != "Listing") {
            // either private or banned sub
            return null
        }

        val data = jsonObj.obj("data") ?: return null

        // JSONArray of children aka posts
        val children = data.array<JsonObject>("children") ?: return null

        // check if children are posts
        if (children.any { it.string("kind") != "t3" }) {
            return null
        }

        val after = data.string("after") ?: ""

        val posts = children.map {
            postAdapter.fromJson(it.toJsonString())!!
        }

        return SubredditListing(
            name = posts.firstOrNull()?.subreddit ?: "",
            before = jsonObj.string("before") ?: "",
            after = after,
            children = posts.toMutableList()
        )
    }
}