package com.example.redfruit.data.api

import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.util.Constants
import com.example.redfruit.util.getResponse
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SubredditAboutRepository(private val subreddits: MutableMap<String, SubredditAbout?>) {

    suspend fun getData(sub: String): SubredditAbout? = subreddits.getOrPut(sub) {
        fetchSubredditAbout(sub)
    }

    suspend fun fetchSubredditAbout(sub: String) = withContext(Dispatchers.Default) {

        val subredditJson = fetchSubredditJson(sub)

        if (subredditJson != null) {
            Gson().fromJson(subredditJson.get("data"), SubredditAbout::class.java)
        } else {
            null
        }
    }

    suspend fun fetchSubredditJson(sub: String) = withContext(Dispatchers.IO) {
        val response = getResponse("${Constants.BASE_URL}$sub/about.json?raw_json=1")
        if(response.isNotBlank()) {
            val jsonResponse = JsonParser().parse(response).asJsonObject
            // t5 represents subreddit
            if (jsonResponse.get("kind")?.asString == "t5") {
                jsonResponse
            } else {
                null
            }
        } else {
            null
        }
    }
}