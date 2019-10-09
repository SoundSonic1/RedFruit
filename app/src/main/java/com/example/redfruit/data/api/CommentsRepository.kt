package com.example.redfruit.data.api

import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.Gildings
import com.example.redfruit.util.Constants
import com.example.redfruit.util.getResponse
import com.google.gson.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

/**
 * Repository for fetching comments
 */
class CommentsRepository(
    private val subreddit: String,
    private val postId: String
) : ICommentsRepository {
    private val gson = GsonBuilder()
        .registerTypeAdapter(Comment::class.java, CommentDeserializer())
        .create()

    /**
     * Returns a list of comments which belong to the post
     */
    override suspend fun getComments(limit: Int): List<Comment> = withContext(Dispatchers.Default)
    {
        val url = "${Constants.BASE_URL}${subreddit}/comments/${postId}.json?limit=${limit}&raw_json=1"
        val response = getResponse(url)
        // bad response
        if (response.isBlank()) return@withContext listOf<Comment>()
        val resp = JsonParser().parse(response)

        val jsonArrayResponse = if (resp.isJsonArray) {
            resp.asJsonArray
        } else {
            return@withContext listOf<Comment>()
        }

        val comments = jsonArrayResponse.filter { elem ->
            elem.asJsonObject.get("kind")?.asString == "Listing" &&
                    elem.asJsonObject.get("data").asJsonObject.get("children").asJsonArray.any {
                        it.asJsonObject.get("kind").asString == "t1"
                    }
        }.flatMap {
            parseComments(it.asJsonObject)
        }

        comments
    }

    private fun parseComments(jsonObj: JsonObject): List<Comment> {

        val jsonData = jsonObj.getAsJsonObject("data")
        val children = jsonData.getAsJsonArray("children").filter {
            it.asJsonObject.get("kind").asString == "t1"
        }.map {
            gson.fromJson<Comment>(it, Comment::class.java)
        }

        return children
    }

    private inner class CommentDeserializer : JsonDeserializer<Comment> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Comment {
            val jsonData = json!!.asJsonObject.getAsJsonObject("data")

            val jsonReplies = if(jsonData.get("replies").isJsonObject) {
                jsonData.getAsJsonObject("replies")
            } else {
                null
            }

            return Comment(
                author = jsonData?.get("author")?.asString ?: "Unknown",
                body = jsonData?.get("body")?.asString ?: "",
                created = jsonData.get("created").asLong,
                created_utc = jsonData.get("created_utc").asLong,
                gildings = gson.fromJson(jsonData.get("gildings"), Gildings::class.java),
                id = jsonData.get("id").asString,
                score = jsonData?.get("score")?.asInt ?: 0,
                replies = if (jsonReplies != null) {
                    parseComments(jsonReplies)
                } else {
                    listOf()
                },
                depth = jsonData.get("depth").asInt
            )
        }
    }
}