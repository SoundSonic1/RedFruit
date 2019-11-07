package com.example.redfruit.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.BuildConfig
import com.example.redfruit.R
import com.example.redfruit.data.adapter.CommentsAdapter
import com.example.redfruit.data.adapter.SubredditAboutAdapter
import com.example.redfruit.data.adapter.SubredditListingAdapter
import com.example.redfruit.data.adapter.SubredditsAdapter
import com.example.redfruit.data.api.*
import com.example.redfruit.data.model.Token
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.util.Constants
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("ApplicationContext")
    fun provideApplicationContext(app: Application): Context = app

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Named("savedSub")
    fun provideSavedSub(
        pref: SharedPreferences,
        @Named("ApplicationContext") context: Context
    ) = pref.getString(
            context.getString(R.string.saved_subreddit),
            Constants.DEFAULT_SUB
        ) ?: Constants.DEFAULT_SUB

    @Provides
    @Named("savedSorting")
    fun provideSavedSortPref(
        pref: SharedPreferences
    ): SortBy {
        val sort = pref.getString(Constants.SORTBY_SHARED_KEY, SortBy.hot.name) ?: SortBy.hot.name
        return SortBy.valueOf(sort)
    }

    @Provides
    @Singleton
    @Named("DeviceId")
    fun provideDeviceId(sharedPref: SharedPreferences): String {
        val id = sharedPref.getString(Constants.DEVICE_ID_KEY, null)
        if (id.isNullOrBlank()) {
            val newId = UUID.randomUUID().toString()
            sharedPref.edit {
                putString(Constants.DEVICE_ID_KEY, newId)
            }
            return newId
        } else {
            return id
        }
    }

    @Provides
    fun provideSavedToken(sharedPref: SharedPreferences): Token? {

        val accessCode = sharedPref.getString(Constants.TOKEN_KEY, "") ?: ""

        if (accessCode.isNotBlank()) {
            return Token(accessCode)
        }

        return null
    }

    @Provides
    fun provideOAuthApi(): OAuthApi = Retrofit
        .Builder()
        .baseUrl(Constants.ACCESS_TOKEN_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(OAuthApi::class.java)

    @Provides
    fun provideMoshi(
        subredditListingAdapter: SubredditListingAdapter,
        subredditAboutAdapter: SubredditAboutAdapter,
        subredditsAdapter: SubredditsAdapter,
        commentsAdapter: CommentsAdapter
    ) = Moshi.Builder()
        .add(subredditListingAdapter)
        .add(subredditAboutAdapter)
        .add(subredditsAdapter)
        .add(commentsAdapter)
        .build()

    @Provides
    @Singleton
    fun provideTokenProvider(
        @Named("DeviceId") deviceId: String,
        token: Token?,
        oAuthApi: OAuthApi,
        sharedPref: SharedPreferences
    ): ITokenProvider = TokenProvider(BuildConfig.ClientId, deviceId, oAuthApi, token) {
        it?.let {
            sharedPref.edit {
                putString(Constants.TOKEN_KEY, it.access)
            }
        }
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(tokenProvider: ITokenProvider) = TokenAuthenticator(tokenProvider)

    @Provides
    fun provideTokenInterceptor(tokenAuthenticator: TokenAuthenticator) = TokenInterceptor(tokenAuthenticator)

    @Provides
    @Singleton
    fun provideOkhttpClient(
        authenticator: TokenAuthenticator, interceptor: TokenInterceptor
    ) = OkHttpClient.Builder().addInterceptor(interceptor).authenticator(authenticator).build()

    @Provides
    fun provideRedditApi(
        moshi: Moshi,
        client: OkHttpClient
    ): RedditApi = Retrofit
        .Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(RedditApi::class.java)

    @Provides
    fun provideSubredditAboutRepo(api: RedditApi) = SubredditAboutRepository(api)

    @Provides
    @Named("ItemAnimator")
    fun provideItemAnimator(): RecyclerView.ItemAnimator = SlideInDownAnimator()
}
