package com.example.redfruit.data.api

import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.SubredditListing
import com.example.redfruit.data.model.enumeration.SortBy

/**
 * Api interface for reddit get/post requests
 */
interface IRedditApi {

    suspend fun getSubredditListing(
        subreddit: String, sortBy: SortBy, after: String, limit: Int
    ): SubredditListing?

    suspend fun getSubreddditAbout(subreddit: String): SubredditAbout?

    suspend fun getComments(subreddit: String, postId: String, limit: Int): List<Comment>

    suspend fun findSubreddits(query: String, limit: Int): List<SubredditAbout>
}