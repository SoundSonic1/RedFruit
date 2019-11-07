package com.example.redfruit.data.adapter

import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.SubredditListing
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import javax.inject.Inject

class SubredditListingAdapter @Inject constructor() {

    private val moshi = Moshi.Builder().add(PostAdapter()).build()

    private val mapAdapter = moshi.adapter(Map::class.java)

    private val postAdapter = moshi.adapter(Post::class.java)

    @FromJson
    fun fromJson(jsonMap: Map<*, *>): SubredditListing? {

        // either private or banned sub
        if (jsonMap["kind"] != "Listing") return null

        val data = jsonMap["data"] as? Map<*, *> ?: return null

        @Suppress("UNCHECKED_CAST")
        val children = data["children"] as? List<Map<*, *>>?: return null

        // check if children are posts
        if (children.any { it["kind"] != "t3" }) return null

        val posts = children.map {
            postAdapter.fromJson(mapAdapter.toJson(it))!!
        }

        return SubredditListing(
            name = posts.firstOrNull()?.subreddit ?: "",
            before = data["before"] as? String ?: "",
            after = data["after"] as? String ?: "",
            children = posts.toMutableList()
        )
    }
}
