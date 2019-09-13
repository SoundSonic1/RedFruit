package com.example.redfruit.di

import com.example.redfruit.ui.comments.fragment.CommentsFragment
import com.example.redfruit.ui.home.fragment.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBindingModule {
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun fragmentHome(): HomeFragment
    @ContributesAndroidInjector
    abstract fun fragmentComments(): CommentsFragment
}