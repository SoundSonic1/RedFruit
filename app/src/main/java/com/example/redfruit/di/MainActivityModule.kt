package com.example.redfruit.di

import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.shared.SubredditVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideHomeFragment() = HomeFragment()

        @JvmStatic
        @Provides
        fun provideSubredditVMFactory(@Named("savedSub") savedSub: String) =
            SubredditVMFactory(savedSub)
    }

}