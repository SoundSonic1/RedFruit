package com.example.redfruit.data.repositories

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.deserializer.CommentDeserializer
import com.example.redfruit.data.model.Comment
import com.example.redfruit.util.KlaxonFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching comments
 */
class CommentsRepository(
    private val redditApi: IRedditApi,
    private val subreddit: String,
    private val postId: String
) : ICommentsRepository {

    private val deserializer = CommentDeserializer(KlaxonFactory())
    private val parser = Parser.default()

    /**
     * Returns a list of comments which belong to the post
     */
    override suspend fun getComments(limit: Int): List<Comment> = withContext(Dispatchers.Default) {
        val response = redditApi.getComments(subreddit, postId, limit)
        // bad response
        if (response.isBlank()) return@withContext listOf<Comment>()

        val stringBuilder = StringBuilder(response)

        val array = try {
            @Suppress("UNCHECKED_CAST")
            parser.parse(stringBuilder) as JsonArray<JsonObject>
        } catch (e: Throwable) {
            return@withContext listOf<Comment>()
        }

        val comments = array.filter {
            it.string("kind") == "Listing" && it.obj("data")?.array<JsonObject>("children")?.any {
                it.string("kind") == "t1"
            } ?: false
        }.flatMap {
            deserializer.deserialize(it)
        }

        comments
    }
}