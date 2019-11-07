package com.example.redfruit

import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.util.Constants
import com.example.redfruit.util.provideOAuthApi
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class TokenAuthenticatorTest {

    private val oauthApi = provideOAuthApi()

    @Test
    fun `automatic refresh of expired token`() {

        val tokenProvider = TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString(), oauthApi)

        val request = Request.Builder().apply {
            addHeader("User-Agent", Constants.USER_AGENT)
            // old token
            addHeader("Authorization", "Bearer 1234")
            url("https://oauth.reddit.com/r/androiddev/hot")
        }.build()

        val client = OkHttpClient.Builder()
            .authenticator(TokenAuthenticator(tokenProvider))
            .build()

        val response = client.newCall(request).execute()
        assertTrue(response.isSuccessful)
    }
}