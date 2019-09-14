package com.example.redfruit.di

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.R
import com.example.redfruit.data.api.SubRedditRepository
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.comments.fragment.CommentsFragment
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.home.viewmodel.HomeVMFactory
import com.example.redfruit.ui.home.viewmodel.HomeViewModel
import com.example.redfruit.util.Constants
import com.example.redfruit.util.findFragmentByTag
import com.example.redfruit.util.replaceFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
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
        fun provideFragmentManager(homeFragment: HomeFragment) = homeFragment.fragmentManager

        @JvmStatic
        @Provides
        fun provideHomeAdapter(
            fragmentManager: FragmentManager?,
            uiScope: CoroutineScope
        ): HomeAdapter {
            return HomeAdapter(uiScope, mutableListOf()) {
                fragmentManager?.let {
                    val fragment = findFragmentByTag(it, Constants.COMMENTS_FRAGMENT_TAG) {
                        CommentsFragment()
                    }
                    replaceFragment(it, R.id.mainContent, fragment, Constants.COMMENTS_FRAGMENT_TAG)
                }
            }
        }

        @JvmStatic
        @Provides
        fun provideHomeViewModel(
            fragment: HomeFragment,
            @Named("savedSub") sub: String,
            repo: SubRedditRepository
        ): HomeViewModel {
            return ViewModelProvider(
                fragment,
                HomeVMFactory(sub, SortBy.new, repo)).get(HomeViewModel::class.java
            )
        }
    }
}