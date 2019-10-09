package com.example.redfruit

import com.example.redfruit.data.api.TokenProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*


class TokenProviderTest {
    private lateinit var tokenProvider: TokenProvider

    @Before
    fun init() {
        tokenProvider = TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString())
    }

    @Test
    fun testUserLessToken() {
        val token = tokenProvider.refreshToken()!!

        Assert.assertTrue(token.access.isNotBlank())
        Assert.assertEquals("bearer",token.type)
        Assert.assertTrue(token.expires_in > 0)
    }

    @Test
    fun testEmptyId() {
        tokenProvider = TokenProvider("", UUID.randomUUID().toString())
        val token = tokenProvider.refreshToken()
        Assert.assertNull(token)
    }

    @Test
    fun testGetToken() {
        Assert.assertNull(tokenProvider.token)
        tokenProvider.refreshToken()
        Assert.assertNotNull(tokenProvider.token)
    }
}