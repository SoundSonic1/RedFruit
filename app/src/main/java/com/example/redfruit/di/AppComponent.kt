package com.example.redfruit.di

import android.app.Application
import com.example.redfruit.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivitiesBindingModule::class,
        FragmentsBindingModule::class
    ]
)

interface AppComponent: AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application):Builder
        fun build(): AppComponent
    }
}