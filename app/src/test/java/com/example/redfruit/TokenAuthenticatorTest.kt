package com.example.redfruit

import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.util.Constants
import com.example.redfruit.util.KlaxonFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Assert
import org.junit.Test
import java.util.*

class TokenAuthenticatorTest {

    private val klaxonFactory = KlaxonFactory()

    @Test
    fun testTokenRefresh() {

        val tokenProvider = TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString(), klaxonFactory)

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
        Assert.assertTrue(response.isSuccessful)
    }
}