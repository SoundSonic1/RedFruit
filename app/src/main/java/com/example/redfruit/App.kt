package com.example.redfruit

import com.example.redfruit.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * @author http://www.albertgao.xyz/2018/04/18/dependency-injection-on-android-with-dagger-android-and-kotlin/
 */
class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .create(this)
            .build()
    }
}
