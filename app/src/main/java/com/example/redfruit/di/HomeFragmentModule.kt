package com.example.redfruit.di

import androidx.fragment.app.FragmentManager
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.home.adapter.SubredditPagerAdapter
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.home.fragment.childfragments.SubredditAboutFragment
import com.example.redfruit.ui.home.fragment.childfragments.SubredditPostsFragment
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
        fun provideTabAdapter(fragmentManager: FragmentManager) =
            SubredditPagerAdapter(
                listOf(
                    SubredditPostsFragment.newInstance(SortBy.hot),
                    SubredditPostsFragment.newInstance(SortBy.new),
                    SubredditAboutFragment()
                ),
                listOf(SortBy.hot.name, SortBy.new.name, "about"),
                fragmentManager
            )

    }
}