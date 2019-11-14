package com.example.redfruit.data.api

import com.example.redfruit.util.Constants
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor @Inject constructor(
    private val authenticator: TokenAuthenticator
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .header("User-Agent", Constants.USER_AGENT)
            .header("Authorization", "Bearer ${authenticator.currentToken?.access}")
            .build()

        return chain.proceed(request)
    }
}
