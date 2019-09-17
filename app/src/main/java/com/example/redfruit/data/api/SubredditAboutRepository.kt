package com.example.redfruit.data.api

import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.util.Constants
import com.example.redfruit.util.getResponse
import com.google.gson.Gson
import com.google.gson.JsonParser

class SubredditAboutRepository(private val subreddits: MutableMap<String, SubredditAbout>) {

    fun getData(sub: String): SubredditAbout = subreddits.getOrPut(sub) {
        fetchSubredditAbout(sub)
    }

    fun fetchSubredditAbout(sub: String): SubredditAbout {
        val response = getResponse("${Constants.BASE_URL}${sub}/about/.json?raw_json=1")
        if (response.isBlank()) {
            return SubredditAbout(sub)
        }
        val responseJsonObj = JsonParser().parse(response).asJsonObject
        if (responseJsonObj.get("kind").asString != "t5") {
            return SubredditAbout(sub)
        }

        return Gson().fromJson(responseJsonObj.get("data"), SubredditAbout::class.java)
    }
}