package com.example.redfruit.data.api

import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Responsible for all api requests
 */
class RedditApi(
    private val authenticator: TokenAuthenticator
) : IRedditApi {

    private fun buildRequest(url: HttpUrl) = Request.Builder().apply {
        addHeader("User-Agent", Constants.USER_AGENT)
        addHeader("Authorization", "Bearer ${authenticator.currentToken?.access}")
        url(url)
    }.build()

    private fun createClient() = OkHttpClient.Builder().authenticator(authenticator).build()

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

        val client = createClient()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.let {
                return@withContext it.string()
            }
            ""
        } else {
            ""
        }
    }

    /**
     * Returns the api response for the subreddit about page
     */
    override suspend fun getSubreddditAbout(subreddit: String) = withContext(Dispatchers.IO) {

        val url = HttpUrl.Builder()
            .scheme("https")
            .host(BASE_URL)
            .addPathSegment("r/$subreddit/about")
            .addQueryParameter("raw_json", "1")
            .build()

        val request = buildRequest(url)

        val client = createClient()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.let {
                return@withContext it.string()
            }
            ""
        } else {
            ""
        }
    }

    /**
     * Returns the api response for comments
     */
    override suspend fun getComments(subreddit: String, postId: String, limit: Int) =
        withContext(Dispatchers.IO) {
            val url = HttpUrl.Builder()
                .scheme("https")
                .host(BASE_URL)
                .addPathSegment("r/$subreddit/comments/$postId")
                .addQueryParameter("limit", limit.toString())
                .addQueryParameter("raw_json", "1")
                .build()

            val request = buildRequest(url)

            val client = createClient()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.let {
                    return@withContext it.string()
                }
                ""
            } else {
                ""
            }
        }

    companion object {
        private const val BASE_URL = "oauth.reddit.com"
    }
}