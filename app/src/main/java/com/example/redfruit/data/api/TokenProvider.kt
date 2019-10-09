package com.example.redfruit.data.api

import com.example.redfruit.data.model.Token
import com.example.redfruit.util.Constants
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

class TokenProvider(
    private val clientId: String,
    private val deviceId: String
) : ITokenProvider {

    private var _token: Token? = null

    override val token get() = _token

    override fun refreshToken(): Token? {

        val clientIdPw = "$clientId:"
        val accessTokenUrl = "https://www.reddit.com/api/v1/access_token"
        val grantType = "https://oauth.reddit.com/grants/installed_client"

        val encodeClientID = String(Base64.getEncoder().encode(clientIdPw.toByteArray()))

        val basic = "Basic $encodeClientID"

        val body = FormBody.Builder().apply {
            add("grant_type", grantType)
            add("device_id", deviceId)
        }.build()

        val request = Request.Builder().apply {
            addHeader("User-Agent", Constants.USER_AGENT)
            addHeader("Content-Type", "application/x-www-form-urlencoded")
            addHeader("Authorization", basic)
            post(body)
            url(accessTokenUrl)
        }.build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            _token = Gson().fromJson(response.body?.string(), Token::class.java)
            return token
        } else {
            return null
        }
    }
}