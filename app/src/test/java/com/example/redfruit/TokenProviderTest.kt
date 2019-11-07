package com.example.redfruit

import com.example.redfruit.data.api.TokenProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*


class TokenProviderTest {

    private val tokenProvider = TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString())

    @Test
    fun `get token for application only oauth`() {
        val token = tokenProvider.refreshToken() ?: return fail("Token must not be null")

        assertTrue(token.access.isNotBlank())
        assertEquals("bearer", token.type)
        assertTrue(token.expires_in > 0)
    }

    @Test
    fun `empty Id`() {
        val provider = TokenProvider("", UUID.randomUUID().toString())
        val token = provider.refreshToken()
        assertNull(token)
    }

    @Test
    fun `refresh the token`() {
        assertNull(tokenProvider.token, "Token should be null at initialization")
        tokenProvider.refreshToken()
        assertNotNull(tokenProvider.token, "Token shouldn't be null after refresh")
    }
}