package com.example.redfruit.data.api

import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.enumeration.SortBy

/**
 * Api interface for reddit get/post requests
 */
interface IRedditApi {

    suspend fun getSubredditPosts(subreddit: String, sortBy: SortBy, after: String, limit: Int): String

    suspend fun getSubreddditAbout(subreddit: String): SubredditAbout?

    suspend fun getComments(subreddit: String, postId: String, limit: Int): String

    suspend fun findSubreddits(query: String, limit: Int): List<SubredditAbout>
}