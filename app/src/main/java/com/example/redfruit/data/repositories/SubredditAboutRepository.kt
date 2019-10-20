package com.example.redfruit.data.repositories

import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.model.SubredditAbout

class SubredditAboutRepository(
    private val redditApi: IRedditApi,
    private val subreddits: MutableMap<String, SubredditAbout?>
) {

    suspend fun getData(sub: String): SubredditAbout? = subreddits.getOrPut(sub) {
        redditApi.getSubreddditAbout(sub)
    }

}