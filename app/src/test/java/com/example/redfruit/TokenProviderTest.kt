package com.example.redfruit

import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.util.provideOAuthApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class TokenProviderTest {

    private val oauthApi = provideOAuthApi()

    private val tokenProvider = TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString(), oauthApi)

    @Test
    fun `get token for application only oauth`() {
        runBlocking {
            val token = tokenProvider.refreshToken() ?: fail("Token must not be null")

            assertTrue(token.access.isNotBlank())
            assertEquals("bearer", token.type)
            assertTrue(token.expires_in > 0)
        }
    }

    @Test
    fun `empty Id`() {
        runBlocking {
            val provider = TokenProvider("", UUID.randomUUID().toString(), oauthApi)
            val token = provider.refreshToken()
            assertNull(token)
        }
    }

    @Test
    fun `refresh the token`() {
        runBlocking {
            assertNull(tokenProvider.token, "Token should be null at initialization")
            tokenProvider.refreshToken()
            assertNotNull(tokenProvider.token, "Token shouldn't be null after refresh")
        }
    }

    @Test
    fun `trigger refresh listener`() {
        var triggered = false
        val provider = TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString(), oauthApi) {
           triggered = true
        }
        runBlocking {
            provider.refreshToken()
            assertTrue(triggered)
        }
    }
}