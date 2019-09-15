package com.example.redfruit.di

import androidx.activity.viewModels
import com.example.redfruit.ui.MainActivity
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.shared.SubredditViewModel
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

        @JvmStatic
        @Provides
        fun provideSubredditViewModel(
            mainActivity: MainActivity,
            @Named("savedSub") sub: String
        ): SubredditViewModel {
            val vm by mainActivity.viewModels<SubredditViewModel> {
                BaseVMFactory { SubredditViewModel(sub) }
            }
            return vm
        }
    }

}