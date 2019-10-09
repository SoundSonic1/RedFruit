package com.example.redfruit.data.api

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Custom authenticator which automatically refreshes the token if response is 401
 */
class TokenAuthenticator(
    private val tokenProvider: ITokenProvider
) : Authenticator {

    val currentToken get() = tokenProvider.token

    override fun authenticate(route: Route?, response: Response): Request? {

        synchronized(this) {
            // Check if the request made was previously made as an authenticated request.
            if (response.request.header("Authorization") != null) {

                val updatedToken = tokenProvider.refreshToken() ?: return null

                // Retry the request with the new token.
                return response
                    .request
                    .newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer ${updatedToken.access}")
                    .build()
            }
        }

        return null
    }
}