package com.example.redfruit.di

import androidx.fragment.app.FragmentManager
import com.example.redfruit.ui.home.adapter.SortByPagerAdapter
import com.example.redfruit.ui.home.fragment.HomeFragment
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule {

    @Module
    companion object {

        /**
         * Return childFragmentManager for TabAdapter
         */
        @JvmStatic
        @Provides
        fun provideChildFragmentManager(homeFragment: HomeFragment) =
            homeFragment.childFragmentManager

        @JvmStatic
        @Provides
        fun provideTabAdapter(fragmentManager: FragmentManager) = SortByPagerAdapter(fragmentManager)
    }
}