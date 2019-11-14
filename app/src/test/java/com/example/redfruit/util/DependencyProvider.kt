package com.example.redfruit.util

import com.example.redfruit.BuildConfig
import com.example.redfruit.data.adapter.CommentsAdapter
import com.example.redfruit.data.adapter.SubredditAboutAdapter
import com.example.redfruit.data.adapter.SubredditListingAdapter
import com.example.redfruit.data.adapter.SubredditsAdapter
import com.example.redfruit.data.api.OAuthApi
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenInterceptor
import com.example.redfruit.data.api.TokenProvider
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.UUID

/**
 * Helper functions to provide dependencies for test classes
 */

fun provideOAuthApi(): OAuthApi = Retrofit
    .Builder()
    .baseUrl(Constants.ACCESS_TOKEN_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(OAuthApi::class.java)

fun provideAuthenticator(): TokenAuthenticator {

    val tokenAuthenticator = TokenAuthenticator(
        TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString(), provideOAuthApi())
    )

    return tokenAuthenticator
}

fun provideInterceptor(authenticator: TokenAuthenticator) = TokenInterceptor(authenticator)

fun provideOkhttpClient(authenticator: TokenAuthenticator): OkHttpClient =
    OkHttpClient.Builder().addInterceptor(provideInterceptor(authenticator))
        .authenticator(authenticator).build()

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(SubredditListingAdapter())
    .add(SubredditAboutAdapter())
    .add(SubredditsAdapter())
    .add(CommentsAdapter())
    .build()

fun provideRedditApi(): RedditApi {
    val authenticator = provideAuthenticator()
    val client = provideOkhttpClient(authenticator)
    val moshi = provideMoshi()
    return Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(RedditApi::class.java)
}
