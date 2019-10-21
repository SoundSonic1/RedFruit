package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.model.SubredditAbout

class SubredditAboutRepository(
    private val redditApi: IRedditApi,
    private val subreddits: MutableMap<String, SubredditAbout?> = mutableMapOf(),
    private val queryMap: MutableMap<Pair<String, Int>, List<SubredditAbout>> = mutableMapOf()
) {
    suspend fun getData(sub: String): SubredditAbout? = subreddits.getOrPut(sub) {
        redditApi.getSubreddditAbout(sub)
    }

    suspend fun findSubreddits(query: String, limit: Int) = queryMap.getOrPut(Pair(query, limit)) {
        redditApi.findSubreddits(query, limit)
    }
}