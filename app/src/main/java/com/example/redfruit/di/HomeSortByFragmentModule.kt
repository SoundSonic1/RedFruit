package com.example.redfruit.di

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.R
import com.example.redfruit.data.api.SubRedditRepository
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
        fun provideSubredditRepo() = SubRedditRepository()

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
            homeSortByFragment: HomeSortByFragment,
            @Named("savedSub") savedSub: String,
            sortBy: SortBy,
            repo: SubRedditRepository
        ): HomeViewModel {
            return ViewModelProvider(
                homeSortByFragment,
                BaseVMFactory { HomeViewModel(savedSub, sortBy, repo) }
            ).get(HomeViewModel::class.java)
        }
    }
}