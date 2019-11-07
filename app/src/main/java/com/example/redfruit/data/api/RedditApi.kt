package com.example.redfruit.data.api

import com.example.redfruit.data.adapter.CommentsAdapter
import com.example.redfruit.data.adapter.SubredditAboutAdapter
import com.example.redfruit.data.adapter.SubredditListingAdapter
import com.example.redfruit.data.adapter.SubredditsAdapter
import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.data.model.SubredditListing
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
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
    private val client: OkHttpClient
) : IRedditApi {

    private fun buildRequest(url: HttpUrl) = Request.Builder().apply {
        addHeader("User-Agent", Constants.USER_AGENT)
        addHeader("Authorization", "Bearer ${authenticator.currentToken?.access}")
        url(url)
    }.build()

    private val moshi = Moshi.Builder()
        .add(SubredditListingAdapter())
        .add(SubredditAboutAdapter())
        .add(SubredditsAdapter())
        .add(CommentsAdapter())
        .build()

    private val typeSubreddits =
        Types.newParameterizedType(List::class.java, SubredditAbout::class.java)

    private val typeComments =
        Types.newParameterizedType(List::class.java, Comment::class.java)

    private val subredditListingAdapter = moshi.adapter(SubredditListing::class.java)
    private val subredditAboutAdapter = moshi.adapter(SubredditAbout::class.java)
    private val subredditsAdapter = moshi.adapter<List<SubredditAbout>>(typeSubreddits)

    private val commentsAdapter = moshi.adapter<List<Comment>>(typeComments)

    /**
     * Returns a Pair which contains a new list of posts and a new "after" for pagination
     */
    override suspend fun getSubredditListing(
        subreddit: String,
        sortBy: SortBy,
        after: String,
        limit: Int
    ) = withContext(Dispatchers.Default) {

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

        if (!response.isSuccessful) return@withContext null

        val responseString = response.body?.string() ?: return@withContext null

        subredditListingAdapter.fromJson(responseString)
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

        subredditAboutAdapter.fromJson(responseString)
    }

    /**
     * Returns the api response for comments
     */
    override suspend fun getComments(
        subreddit: String, postId: String, limit: Int
    ) = withContext(Dispatchers.Default) {
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

        try {
            commentsAdapter.fromJson(responseString) ?: listOf()
        } catch (e: Throwable) {
            // response is not in json array format
            listOf<Comment>()
        }
    }

    override suspend fun findSubreddits(query: String, limit: Int): List<SubredditAbout> = withContext(Dispatchers.Default) {

        if (query.isBlank()) return@withContext listOf<SubredditAbout>()

        if (limit < 1) return@withContext listOf<SubredditAbout>()

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

        subredditsAdapter.fromJson(response.body!!.string()) ?: listOf()
    }

    companion object {
        private const val BASE_URL = "oauth.reddit.com"
    }
}