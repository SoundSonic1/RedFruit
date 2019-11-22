package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.Post
import javax.inject.Inject

/**
 * Repository for fetching comments for CommentsViewModel
 */
class CommentsRepository @Inject constructor(
    private val redditApi: RedditApi,
    private val post: Post
) : ICommentsRepository {

    /**
     * Returns a list of comments which belong to the post
     */
    override suspend fun getComments(limit: Int): List<Comment> = try {
        redditApi.getComments(post.subreddit, post.id, limit.toString())
    } catch (e: Throwable) {
        listOf()
    }
}
