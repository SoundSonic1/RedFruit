package com.example.redfruit.di

import android.content.Context
import androidx.cursoradapter.widget.CursorAdapter
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.home.adapter.SuggestionsAdapter
import com.example.redfruit.ui.home.fragment.HomeFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object HomeFragmentModule {

    @Provides
    @JvmStatic
    @Named("HomeFragmentContext")
    fun provideContext(homeFragment: HomeFragment): Context? = homeFragment.context

    /**
     * Return childFragmentManager for TabAdapter
     */
    @Provides
    fun provideChildFragmentManager(homeFragment: HomeFragment) =
        homeFragment.childFragmentManager

    @Provides
    fun provideCategories(@Named("savedSorting") sortBy: SortBy) =
        mutableListOf(sortBy.name, "About")

    @Provides
    fun provideCursorAdapter(@Named("HomeFragmentContext") context: Context?): CursorAdapter =
        SuggestionsAdapter(context, null, 0)
}
