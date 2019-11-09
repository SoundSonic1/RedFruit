package com.example.redfruit.di

import com.example.redfruit.ui.media.fragment.ImageFragment
import com.example.redfruit.util.Constants
import dagger.Module
import dagger.Provides

@Module
object ImageFragmentModule {

    @Provides
    fun provideImageUrl(fragment: ImageFragment) =
        fragment.arguments?.getString(Constants.IMAGE_URL_KEY) ?: ""
}
