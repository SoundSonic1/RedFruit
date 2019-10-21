package com.example.redfruit.di

import android.content.Context
import androidx.cursoradapter.widget.CursorAdapter
import androidx.fragment.app.FragmentManager
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.home.adapter.SubredditPagerAdapter
import com.example.redfruit.ui.home.adapter.SuggestionsAdapter
import com.example.redfruit.ui.home.fragment.HomeFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class HomeFragmentModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Named("HomeFragmentContext")
        fun provideContext(homeFragment: HomeFragment): Context? = homeFragment.context

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

        @JvmStatic
        @Provides
        fun provideCursorAdapter(@Named("HomeFragmentContext") context: Context?): CursorAdapter =
            SuggestionsAdapter(context, null, 0)

    }
}