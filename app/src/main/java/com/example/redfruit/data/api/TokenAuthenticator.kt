package com.example.redfruit.data.api

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Custom authenticator which automatically refreshes the token if response is 401
 */
@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenProvider: TokenProvider
) : Authenticator {

    val currentToken get() = tokenProvider.token

    override fun authenticate(route: Route?, response: Response): Request? {

        val token = currentToken

        synchronized(this) {
            val newToken = currentToken
            // Check if the request was previously made as an authenticated request.
            if (response.request.header("Authorization") != null) {

                // If the token has changed since the request was made, use the new token.
                if (token != newToken) {
                    return response
                        .request
                        .newBuilder()
                        .header("Authorization", "Bearer ${newToken?.access}")
                        .build()
                }

                val updatedToken = runBlocking {
                    tokenProvider.refreshToken()
                } ?: return null

                // Retry the request with the new token.
                return response
                    .request
                    .newBuilder()
                    .header("Authorization", "Bearer ${updatedToken.access}")
                    .build()
            }
        }

        return null
    }
}
