package com.example.redfruit.di

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.R
import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.api.SubredditPostsRepository
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.comments.fragment.CommentsFragment
import com.example.redfruit.ui.home.adapter.PostListAdapter
import com.example.redfruit.ui.home.fragment.childfragments.SubredditPostsFragment
import com.example.redfruit.ui.home.viewmodel.HomePostsViewModel
import com.example.redfruit.util.BaseVMFactory
import com.example.redfruit.util.Constants
import com.example.redfruit.util.addOrShowFragment
import com.example.redfruit.util.findOrCreateFragment
import dagger.Module
import dagger.Provides
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import javax.inject.Named

@Module
class SubredditPostsFragmentModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideSubredditRepo(redditApi: IRedditApi) = SubredditPostsRepository(redditApi)

        @JvmStatic
        @Provides
        fun provideActivityContext(subredditPostsFragment: SubredditPostsFragment) =
            subredditPostsFragment.requireContext()

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
        fun provideHomeListAdapter(
            fm: FragmentManager?,
            homePostsViewModel: HomePostsViewModel
        ): PostListAdapter {
            return PostListAdapter(fm!!) { post ->
                val fragment = findOrCreateFragment(fm, Constants.COMMENTS_FRAGMENT_TAG) {
                    CommentsFragment.newInstance(post)
                }
                addOrShowFragment(fm, R.id.mainContent, fragment, Constants.COMMENTS_FRAGMENT_TAG)

            }.apply {
                submitList(homePostsViewModel.data.value ?: listOf())
            }
        }

        @JvmStatic
        @Provides
        fun provideHomeViewModel(
            fragment: SubredditPostsFragment,
            @Named("savedSub") savedSub: String,
            @Named("savedSorting") sortBy: SortBy,
            repo: SubredditPostsRepository
        ): HomePostsViewModel {
            val vm by fragment.viewModels<HomePostsViewModel> {
                BaseVMFactory { HomePostsViewModel(savedSub, sortBy, repo) }
            }
            return vm
        }

        @JvmStatic
        @Provides
        fun provideSlideInDownAnimator() = SlideInDownAnimator()
    }
}