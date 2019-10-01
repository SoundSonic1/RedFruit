package com.example.redfruit.di

import androidx.activity.viewModels
import com.example.redfruit.data.api.SubredditAboutRepository
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.MainActivity
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.BaseVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MainActivityModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideHomeFragment() = HomeFragment()

        // TODO: inject shared preferences
        @JvmStatic
        @Provides
        fun provideSubredditAboutRepo() = SubredditAboutRepository(mutableMapOf())

        @JvmStatic
        @Provides
        fun provideSubredditViewModel(
            mainActivity: MainActivity,
            @Named("savedSub") sub: String,
            @Named("savedSorting") sortBy: SortBy,
            repo: SubredditAboutRepository
        ): SubredditAboutViewModel {
            val vm by mainActivity.viewModels<SubredditAboutViewModel> {
                BaseVMFactory { SubredditAboutViewModel(sub, sortBy, repo) }
            }
            return vm
        }
    }

}