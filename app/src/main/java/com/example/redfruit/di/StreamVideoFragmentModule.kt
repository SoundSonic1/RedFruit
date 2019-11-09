package com.example.redfruit.di

import android.content.Context
import com.example.redfruit.ui.media.fragment.StreamVideoFragment
import com.example.redfruit.util.Constants
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import dagger.Module
import dagger.Provides

@Module
object StreamVideoFragmentModule {

    @Provides
    fun provideActivityContext(streamVideoFragment: StreamVideoFragment) =
            streamVideoFragment.requireContext()

    @Provides
    fun provideExoPlayer(context: Context): ExoPlayer =
        ExoPlayerFactory.newSimpleInstance(context)

    @Provides
    fun provideStreamUrl(fragment: StreamVideoFragment) =
        fragment.arguments?.getString(Constants.STREAM_URL_KEY) ?: ""
}
