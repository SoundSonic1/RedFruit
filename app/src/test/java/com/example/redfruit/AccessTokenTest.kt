package com.example.redfruit

import com.example.redfruit.data.model.Token
import com.example.redfruit.util.createTokenRequest
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import java.util.*


class AccessTokenTest {

    @Test
    fun testUserLessToken() {
        val request = createTokenRequest(BuildConfig.ClientId, UUID.randomUUID().toString())

        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        Assert.assertTrue(response.isSuccessful)

        val token = Gson().fromJson(response.body?.string(), Token::class.java)
        Assert.assertTrue(token.access.isNotBlank())
        Assert.assertEquals("bearer",token.type)
        Assert.assertTrue(token.expires_in > 0)
    }
}