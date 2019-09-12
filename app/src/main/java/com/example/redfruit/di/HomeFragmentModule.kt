package com.example.redfruit.di

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.data.api.SubRedditRepository
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.viewmodel.HomeVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class HomeFragmentModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideSubredditRepo() = SubRedditRepository()

        @JvmStatic
        @Provides
        fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

        @JvmStatic
        @Provides
        fun provideHomeAdapter(context: Context) = HomeAdapter(mutableListOf()) {
            Toast.makeText(context,
                "Clicked on ${it.title}", Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        @Provides
        fun provideHomeVMFactory(@Named("savedSub") savedSub: String,
                                 repo: SubRedditRepository) = HomeVMFactory(savedSub, repo)
    }
}