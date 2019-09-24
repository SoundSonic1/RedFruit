package com.example.redfruit.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.redfruit.R
import com.example.redfruit.util.Constants
import dagger.Module
import dagger.Provides
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
}
