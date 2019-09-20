package com.example.redfruit.di

import com.example.redfruit.ui.comments.fragment.CommentsFragment
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.home.fragment.childfragments.SubredditPostsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBindingModule {
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun fragmentHome(): HomeFragment

    @ContributesAndroidInjector(modules = [SubredditPostsFragmentModule::class])
    abstract fun fragmentHomeSortBy(): SubredditPostsFragment

    @ContributesAndroidInjector
    abstract fun fragmentComments(): CommentsFragment
}