package com.example.redfruit.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.redfruit.R
import com.example.redfruit.util.Constants
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    fun provideCoroutineScopeMain() = CoroutineScope(Dispatchers.Main)

    @Provides
    @Named("savedSub")
    fun provideSavedSub(pref: SharedPreferences, context: Context) =
        pref.getString(
            context.getString(R.string.saved_subreddit),
            Constants.DEFAULT_SUB
        ) ?: Constants.DEFAULT_SUB
}
