package com.example.redfruit

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.redfruit.di.DaggerAppComponent
import com.example.redfruit.util.Constants.DARK_THEME_ON_KEY
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .create(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
            sharedPref.getInt(DARK_THEME_ON_KEY, AppCompatDelegate.MODE_NIGHT_NO)
        )
    }
}
