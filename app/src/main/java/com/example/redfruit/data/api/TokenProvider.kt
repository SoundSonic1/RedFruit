package com.example.redfruit.data.api

import android.util.Log
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.example.redfruit.data.model.Token
import com.example.redfruit.util.Constants
import com.example.redfruit.util.IFactory
import com.squareup.moshi.Moshi
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

/**
 * Manages the access token which is required for api calls
 */
class TokenProvider(
    private val clientId: String,
    private val deviceId: String,
    private val klaxonFactory: IFactory<Klaxon>,
    private var _token: Token? = null,
    private val tokenRefreshListener: (Token?) -> Unit = {}
) : ITokenProvider {

    private val moshi = Moshi.Builder().build()
    private val tokenAdapter = moshi.adapter(Token::class.java)

    override val token get() = _token

    /**
     * Refresh the token if the current token is invalid
     */
    override fun refreshToken(): Token? {
        Log.d("TokenAuthenticator", "requesting new token")

        val clientIdPw = "$clientId:"

        val encodeClientID = String(Base64.getEncoder().encode(clientIdPw.toByteArray()))

        val basic = "Basic $encodeClientID"

        val body = FormBody.Builder().apply {
            add("grant_type", GRANT_TYPE)
            add("device_id", deviceId)
        }.build()

        val request = Request.Builder().apply {
            addHeader("User-Agent", Constants.USER_AGENT)
            addHeader("Content-Type", "application/x-www-form-urlencoded")
            addHeader("Authorization", basic)
            post(body)
            url(ACCESS_TOKEN_URL)
        }.build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) return null

        val responseString = response.body?.string() ?: return null
        val responseStringBuilder = StringBuilder(responseString)
        val responsObj = Parser.default().parse(responseStringBuilder) as JsonObject
        _token = tokenAdapter.fromJson(responsObj.toJsonString())

        tokenRefreshListener(token)

        return token

    }

    companion object {
        private const val ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token"
        private const val GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client"
    }
}