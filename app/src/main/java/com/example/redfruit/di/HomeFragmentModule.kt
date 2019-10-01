package com.example.redfruit.di

import androidx.fragment.app.FragmentManager
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.home.adapter.SubredditPagerAdapter
import com.example.redfruit.ui.home.fragment.HomeFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

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
        fun provideTabAdapter(
            fragmentManager: FragmentManager,
            @Named("savedSorting") sortBy: SortBy
        ) = SubredditPagerAdapter(
                mutableListOf(sortBy.name, "About"),
                fragmentManager
            )

    }
}