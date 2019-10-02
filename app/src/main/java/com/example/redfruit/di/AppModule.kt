package com.example.redfruit.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.util.Constants
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
    @Named("DeviceId")
    fun provideDeviceId(sharedPref: SharedPreferences) =
        sharedPref.getString(Constants.DEVICE_ID_KEY, null) ?: UUID.randomUUID().toString()

    @Provides
    @Named("ItemAnimator")
    fun provideItemAnimator(): RecyclerView.ItemAnimator = SlideInDownAnimator()
}
