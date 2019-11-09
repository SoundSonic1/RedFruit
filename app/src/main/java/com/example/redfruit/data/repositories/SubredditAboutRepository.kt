package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.model.SubredditAbout
import javax.inject.Inject

class SubredditAboutRepository(
    private val redditApi: RedditApi,
    private val subreddits: MutableMap<String, SubredditAbout?>,
    private val queryMap: MutableMap<Pair<String, Int>, List<SubredditAbout>>
) {

    @Inject constructor(redditApi: RedditApi) : this(redditApi, mutableMapOf(), mutableMapOf())

    suspend fun getData(sub: String): SubredditAbout? = subreddits.getOrPut(sub) {
        try {
            redditApi.getSubredditAbout(sub)
        } catch (e: Throwable) {
            null
        }
    }

    suspend fun findSubreddits(query: String, limit: Int) = queryMap.getOrPut(Pair(query, limit)) {
        redditApi.findSubreddits(query, limit.toString())
    }
}
