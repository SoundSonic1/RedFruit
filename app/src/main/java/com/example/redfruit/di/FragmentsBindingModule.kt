package com.example.redfruit.di

import com.example.redfruit.ui.comments.fragment.CommentsFragment
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.home.fragment.childfragments.SubredditPostsFragment
import com.example.redfruit.ui.media.fragment.ImageFragment
import com.example.redfruit.ui.media.fragment.StreamVideoFragment
import com.example.redfruit.ui.media.fragment.YoutubeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBindingModule {
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun fragmentHome(): HomeFragment

    @ContributesAndroidInjector(modules = [SubredditPostsFragmentModule::class])
    abstract fun fragmentSubredditPostsFragment(): SubredditPostsFragment

    @ContributesAndroidInjector(modules = [CommentsFragmentModule::class])
    abstract fun fragmentComments(): CommentsFragment

    @ContributesAndroidInjector(modules = [StreamVideoFragmentModule::class])
    abstract fun fragmentStreamVideo(): StreamVideoFragment

    @ContributesAndroidInjector(modules = [ImageFragmentModule::class])
    abstract fun fragmentImage(): ImageFragment

    @ContributesAndroidInjector(modules = [YoutubeFragmentModule::class])
    abstract fun fragmentYoutube(): YoutubeFragment
}
