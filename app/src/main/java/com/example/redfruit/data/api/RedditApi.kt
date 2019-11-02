package com.example.redfruit.data.api

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.example.redfruit.data.deserializer.CommentDeserializer
import com.example.redfruit.data.deserializer.SubredditsDeserializer
import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.util.Constants
import com.example.redfruit.util.IFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Responsible for all api requests to reddit
 */
class RedditApi(
    private val authenticator: TokenAuthenticator,
    private val client: OkHttpClient,
    private val klaxonFactory: IFactory<Klaxon>,
    private val parser: Parser
) : IRedditApi {

    private val commentDeserializer = CommentDeserializer(klaxonFactory)
    private val subredditsDeserializer = SubredditsDeserializer(klaxonFactory)

    private fun buildRequest(url: HttpUrl) = Request.Builder().apply {
        addHeader("User-Agent", Constants.USER_AGENT)
        addHeader("Authorization", "Bearer ${authenticator.currentToken?.access}")
        url(url)
    }.build()

    /**
     * Returns api response for the subreddit posts
     */
    override suspend fun getSubredditPosts(
        subreddit: String,
        sortBy: SortBy,
        after: String,
        limit: Int
    ) = withContext(Dispatchers.IO) {

        val url = HttpUrl.Builder()
            .scheme("https")
            .host(BASE_URL)
            .addPathSegment("r/$subreddit/${sortBy.name}")
            .addQueryParameter("after", after)
            .addQueryParameter("limit", limit.toString())
            .addQueryParameter("raw_json", "1")
            .build()

        val request = buildRequest(url)

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) return@withContext ""

        response.body?.string() ?: ""
    }

    /**
     * Returns the api response for the subreddit about page
     */
    override suspend fun getSubreddditAbout(subreddit: String) = withContext(Dispatchers.Default) {

        val url = HttpUrl.Builder()
            .scheme("https")
            .host(BASE_URL)
            .addPathSegment("r/$subreddit/about")
            .addQueryParameter("raw_json", "1")
            .build()

        val request = buildRequest(url)

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) return@withContext null

        val responseString = response.body?.string() ?: return@withContext null

        val jsonString = StringBuilder(responseString)

        val json = parser.parse(jsonString) as? JsonObject
        json?.let {
            if (json.string("kind") == "t5") {
                json.obj("data")?.let { dataObj ->
                    return@withContext klaxonFactory.build().parseFromJsonObject<SubredditAbout>(dataObj)
                }
            }
        }

        null
    }

    /**
     * Returns the api response for comments
     */
    override suspend fun getComments(subreddit: String, postId: String, limit: Int) =
        withContext(Dispatchers.Default) {
            val url = HttpUrl.Builder()
                .scheme("https")
                .host(BASE_URL)
                .addPathSegment("r/$subreddit/comments/$postId")
                .addQueryParameter("limit", limit.toString())
                .addQueryParameter("raw_json", "1")
                .build()

            val request = buildRequest(url)

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext listOf<Comment>()
            }

            val responseString = response.body?.string() ?: return@withContext listOf<Comment>()

            val jsonStringBuilder = StringBuilder(responseString)

            val array = try {
                @Suppress("UNCHECKED_CAST")
                parser.parse(jsonStringBuilder) as JsonArray<JsonObject>
            } catch (e: Throwable) {
                return@withContext listOf<Comment>()
            }

            array.filter {
                it.string("kind") == "Listing" && it.obj("data")?.array<JsonObject>("children")?.any {
                    it.string("kind") == "t1"
                } ?: false
            }.flatMap {
                commentDeserializer.deserialize(it)
            }
        }

    override suspend fun findSubreddits(query: String, limit: Int): List<SubredditAbout> = withContext(Dispatchers.Default) {

        if (query.isBlank()) return@withContext listOf<SubredditAbout>()

        val url = HttpUrl.Builder()
            .scheme("https")
            .host(BASE_URL)
            .addPathSegments("/subreddits/search")
            .addQueryParameter("q", query)
            .addQueryParameter("limit", limit.toString())
            .addQueryParameter("raw_json", "1")
            .build()

        val request = buildRequest(url)

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) return@withContext listOf<SubredditAbout>()

        response.body?.let {

            val stringBuilder = StringBuilder(it.string())
            val json = parser.parse(stringBuilder) as? JsonObject
            json?.let { jsonObj ->
                return@withContext subredditsDeserializer.deserialize(jsonObj)
            }
        }

        listOf<SubredditAbout>()
    }

    companion object {
        private const val BASE_URL = "oauth.reddit.com"
    }
}