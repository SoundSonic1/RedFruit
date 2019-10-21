package com.example.redfruit.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.example.redfruit.BuildConfig
import com.example.redfruit.R
import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.util.Constants
import com.example.redfruit.util.IFactory
import com.example.redfruit.util.KlaxonFactory
import dagger.Module
import dagger.Provides
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
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
    @Singleton
    fun provideTokenProvider(@Named("DeviceId") deviceId: String)
            = TokenProvider(BuildConfig.ClientId, deviceId)

    @Provides
    @Singleton
    fun provideTokenAuthenticator(tokenProvider: TokenProvider) = TokenAuthenticator(tokenProvider)

    @Provides
    fun provideKlaxonFactory(): IFactory<Klaxon> = KlaxonFactory()

    @Provides
    fun provideParser() = Parser.default()

    @Provides
    @Singleton
    fun provideRedditApi(
        authenticator: TokenAuthenticator, klaxonFactory: IFactory<Klaxon>, parser: Parser
    ): IRedditApi = RedditApi(authenticator, klaxonFactory, parser)

    @Provides
    fun provideSubredditAboutRepo(redditApi: IRedditApi) = SubredditAboutRepository(redditApi)

    @Provides
    @Named("ItemAnimator")
    fun provideItemAnimator(): RecyclerView.ItemAnimator = SlideInDownAnimator()
}
