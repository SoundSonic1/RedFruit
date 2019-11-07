package com.example.redfruit.data.api

import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.SubredditListing
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditApi {

    @GET("r/{subreddit}/{sort}")
    suspend fun getSubredditListing(
        @Path("subreddit") subreddit: String,
        @Path("sort") sort: String,
        @Query("after") after: String,
        @Query("limit") limit: String,
        @Query("raw_json") rawJson: String = "1"
    ): SubredditListing?

    @GET("r/{subreddit}/about")
    suspend fun getSubredditAbout(
        @Path("subreddit") subreddit: String,
        @Query("raw_json") rawJson: String = "1"
    ): SubredditAbout?

    @GET("/subreddits/search")
    suspend fun findSubreddits(
        @Query("q") query: String,
        @Query("limit") limit: String,
        @Query("raw_json") rawJson: String = "1"
    ): List<SubredditAbout>

    @GET("r/{subreddit}/comments/{postId}")
    suspend fun getComments(
        @Path("subreddit") subreddit: String,
        @Path("postId") postId: String,
        @Query("limit") limit: String,
        @Query("raw_json") rawJson: String = "1"
    ): List<Comment>
}