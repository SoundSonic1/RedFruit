package com.example.redfruit.di

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.R
import com.example.redfruit.data.api.SubredditPostsRepository
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.comments.fragment.CommentsFragment
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.fragment.childfragments.SubredditPostsFragment
import com.example.redfruit.ui.home.viewmodel.HomePostsViewModel
import com.example.redfruit.util.BaseVMFactory
import com.example.redfruit.util.Constants
import com.example.redfruit.util.addOrShowFragment
import com.example.redfruit.util.findOrCreateFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SubredditPostsFragmentModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideSubredditRepo() = SubredditPostsRepository()

        @JvmStatic
        @Provides
        fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

        /**
         * Require activity fragment manager to swap to CommentFragment
         */
        @JvmStatic
        @Provides
        fun provideFragmentManager(subredditPostsFragment: SubredditPostsFragment) =
            subredditPostsFragment.activity?.supportFragmentManager

        @JvmStatic
        @Provides
        fun provideHomeAdapter(
            fragmentManager: FragmentManager?,
            homePostsViewModel: HomePostsViewModel
        ): HomeAdapter {
            // restore data if possible
            val dataList = homePostsViewModel.data.value?.toMutableList() ?: mutableListOf()

            return HomeAdapter(fragmentManager!!, dataList) {
                fragmentManager.let { fm ->
                    val fragment = findOrCreateFragment(fm, Constants.COMMENTS_FRAGMENT_TAG) {
                        CommentsFragment()
                    }
                    addOrShowFragment(fm, R.id.mainContent, fragment, Constants.COMMENTS_FRAGMENT_TAG)
                }
            }
        }

        @JvmStatic
        @Provides
        fun provideSortBy(subredditPostsFragment: SubredditPostsFragment): SortBy {
            val sortByString =
                subredditPostsFragment.arguments?.getString(Constants.SORT_BY_KEY) ?: "hot"

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
            fragment: SubredditPostsFragment,
            @Named("savedSub") savedSub: String,
            sortBy: SortBy,
            repo: SubredditPostsRepository
        ): HomePostsViewModel {
            val vm by fragment.viewModels<HomePostsViewModel> {
                BaseVMFactory { HomePostsViewModel(savedSub, sortBy, repo) }
            }
            return vm
        }
    }
}