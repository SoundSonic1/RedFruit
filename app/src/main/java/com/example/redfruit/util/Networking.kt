package com.example.redfruit.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Request
import java.net.URL
import java.util.*

/**
 * @param url to fetch response
 * @return the response as a string
 */
suspend fun getResponse(url: String): String = withContext(Dispatchers.IO) {
    try {
        URL(urlEncode(url)).openConnection().apply {
            setRequestProperty("User-Agent", Constants.USER_AGENT)
        }.getInputStream().use {
            it.bufferedReader().readLine()
        }
    } catch (e: Exception) {
        Log.d("getResponse", "bad url: $url")
        // invalid url
        ""
    }
}

// TODO: use library for url encoding
fun urlEncode(url: String): String = url.replace(" ", "%20")

/**
 * Returns request for new access token
 */
fun createTokenRequest(clientId: String, deviceId: String): Request {
    val client = "$clientId:"
    val accessTokenUrl = "https://www.reddit.com/api/v1/access_token"
    val grantType = "https://oauth.reddit.com/grants/installed_client"

    val encodeClientID = String(Base64.getEncoder().encode(client.toByteArray()))

    val basic = "Basic $encodeClientID"

    val body = FormBody.Builder().apply {
        add("grant_type", grantType)
        add("device_id", deviceId)
    }.build()

    return Request.Builder().apply {
        addHeader("User-Agent", Constants.USER_AGENT)
        addHeader("Content-Type", "application/x-www-form-urlencoded")
        addHeader("Authorization", basic)
        post(body)
        url(accessTokenUrl)
    }.build()
}
