package com.example.redfruit

import com.example.redfruit.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .create(this)
            .build()
    }
}
