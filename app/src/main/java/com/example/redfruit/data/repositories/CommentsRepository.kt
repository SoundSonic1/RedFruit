package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.model.Comment
import javax.inject.Inject
import javax.inject.Named

/**
 * Repository for fetching comments
 */
class CommentsRepository @Inject constructor(
    private val redditApi: RedditApi,
    @Named("SubredditName") private val subreddit: String,
    @Named("PostId") private val postId: String
) : ICommentsRepository {

    /**
     * Returns a list of comments which belong to the post
     */
    override suspend fun getComments(limit: Int): List<Comment> = try {
        redditApi.getComments(subreddit, postId, limit.toString())
    } catch (e: Throwable) {
        listOf()
    }
}
