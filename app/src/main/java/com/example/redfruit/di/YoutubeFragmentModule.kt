package com.example.redfruit.di

import com.example.redfruit.ui.media.fragment.YoutubeFragment
import com.example.redfruit.util.Constants
import dagger.Module
import dagger.Provides

@Module
object YoutubeFragmentModule {

    @Provides
    fun provideYoutubeId(fragment: YoutubeFragment) =
        fragment.arguments?.getString(Constants.YOUTUBE_ID_KEY) ?: ""
}