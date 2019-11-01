package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.model.Comment

/**
 * Repository for fetching comments
 */
class CommentsRepository(
    private val redditApi: IRedditApi,
    private val subreddit: String,
    private val postId: String
) : ICommentsRepository {

    /**
     * Returns a list of comments which belong to the post
     */
    override suspend fun getComments(limit: Int): List<Comment> =
        redditApi.getComments(subreddit, postId, limit)

}