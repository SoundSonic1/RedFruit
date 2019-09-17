package com.example.redfruit.di

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.R
import com.example.redfruit.data.api.SubRedditPostsRepository
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.comments.fragment.CommentsFragment
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.fragment.HomeSortByFragment
import com.example.redfruit.ui.home.viewmodel.HomeViewModel
import com.example.redfruit.util.BaseVMFactory
import com.example.redfruit.util.Constants
import com.example.redfruit.util.findFragmentByTag
import com.example.redfruit.util.replaceFragment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named

@Module
class HomeSortByFragmentModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideSubredditRepo() = SubRedditPostsRepository()

        @JvmStatic
        @Provides
        fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

        /**
         * Require activity fragment manager to swap to CommentFragment
         */
        @JvmStatic
        @Provides
        fun provideFragmentManager(homeSortByFragment: HomeSortByFragment) =
            homeSortByFragment.activity?.supportFragmentManager

        @JvmStatic
        @Provides
        fun provideLifeCycle(fragment: HomeSortByFragment) = fragment.lifecycle

        @JvmStatic
        @Provides
        fun provideHomeAdapter(
            fragmentManager: FragmentManager?,
            uiScope: CoroutineScope,
            lifecycle: Lifecycle
        ): HomeAdapter {
            return HomeAdapter(mutableListOf(), uiScope, lifecycle) {
                fragmentManager?.let { fm ->
                    val fragment = findFragmentByTag(fm, Constants.COMMENTS_FRAGMENT_TAG) {
                        CommentsFragment()
                    }
                    replaceFragment(fm, R.id.mainContent, fragment, Constants.COMMENTS_FRAGMENT_TAG)
                }
            }
        }

        @JvmStatic
        @Provides
        fun provideSortBy(homeSortByFragment: HomeSortByFragment): SortBy {
            val sortByString =
                homeSortByFragment.arguments?.getString(Constants.SORT_BY_KEY) ?: "hot"

            try {
                return SortBy.valueOf(sortByString)
            } catch (e: IllegalArgumentException) {
                Log.d("SortBy", "Illegal SortBy argument: ${sortByString}")
                return SortBy.hot
            }
        }

        @JvmStatic
        @Provides
        fun provideHomeViewModel(
            fragment: HomeSortByFragment,
            @Named("savedSub") savedSub: String,
            sortBy: SortBy,
            repo: SubRedditPostsRepository
        ): HomeViewModel {
            val vm by fragment.viewModels<HomeViewModel> {
                BaseVMFactory { HomeViewModel(savedSub, sortBy, repo) }
            }
            return vm
        }
    }
}