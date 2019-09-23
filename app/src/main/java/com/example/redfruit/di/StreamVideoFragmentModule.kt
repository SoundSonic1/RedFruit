package com.example.redfruit.di

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import dagger.Module
import dagger.Provides

@Module
class StreamVideoFragmentModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideExoPlayer(context: Context): ExoPlayer =
            ExoPlayerFactory.newSimpleInstance(context)
    }
}