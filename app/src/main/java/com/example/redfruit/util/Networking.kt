package com.example.redfruit.util

import android.util.Log
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.net.URL

/**
 * @param url to fetch response
 * @return the response as a string
 */
fun getResponse(url: String): String = try {
    URL(url).readText()
} catch (e: FileNotFoundException) {
    Log.d("getResponse", "bad url: $url")
    // invalid url
    ""
}

/**
 * Returns true if the given sub name is a valid subreddit
 */
suspend fun isValidSubDetail(sub: String): Boolean =
    withContext(Dispatchers.Default) {
        val response = getResponse("${Constants.baseUrl}$sub/about.json?raw_json=1")
        if(response.isBlank()) {
            false
        } else {
            val jsonResponse = JsonParser().parse(response).asJsonObject
            // t5 represents subreddit
            jsonResponse.get("kind")?.asString == "t5"
        }
    }
