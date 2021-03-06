package com.example.redfruit.data.model

import com.example.redfruit.data.model.interfaces.IListing
import com.squareup.moshi.JsonClass

/**
 * Contains subreddit posts and before/after values for pagination
 *
 * @property name of the subreddit
 * @property before bookmark first post in children
 * @property after bookmark last post in children
 * @property children sublist of the subreddit posts
 */
@JsonClass(generateAdapter = true)
data class SubredditListing(
    val name: String,
    override var before: String = "",
    override var after: String = "",
    override val children: MutableList<Post> = mutableListOf()
) : IListing<Post>
