package com.example.redfruit.di

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.data.model.enumeration.SortPostBy
import com.example.redfruit.data.repositories.SubredditPostsRepository
import com.example.redfruit.ui.home.adapter.PostListAdapter
import com.example.redfruit.ui.home.fragment.childfragments.SubredditPostsFragment
import com.example.redfruit.ui.home.viewmodel.HomePostsViewModel
import com.example.redfruit.ui.shared.OnPostClickHandler
import com.example.redfruit.ui.shared.OnPostClickHandlerImpl
import com.example.redfruit.util.BaseVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator

@Module
object SubredditPostsFragmentModule {

    @Provides
    fun provideActivityContext(subredditPostsFragment: SubredditPostsFragment) =
        subredditPostsFragment.requireContext()

    @Provides
    fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

    /**
     * Require activity fragment manager to swap to CommentFragment
     */
    @Provides
    fun provideFragmentManager(subredditPostsFragment: SubredditPostsFragment): FragmentManager =
        subredditPostsFragment.activity!!.supportFragmentManager

    @Provides
    fun provideOnPostImageClickHandler(fm: FragmentManager): OnPostClickHandler =
        OnPostClickHandlerImpl(fm)

    @Provides
    fun provideHomeListAdapter(
        onPostClickHandler: OnPostClickHandler,
        homePostsViewModel: HomePostsViewModel
    ): PostListAdapter {
        return PostListAdapter(onPostClickHandler).apply {
            submitList(homePostsViewModel.data.value ?: listOf())
        }
    }

    @Provides
    fun provideHomeViewModel(
        fragment: SubredditPostsFragment,
        @Named("savedSub") savedSub: String,
        @Named("savedSorting") sortPostBy: SortPostBy,
        repo: SubredditPostsRepository
    ): HomePostsViewModel {
        val vm by fragment.viewModels<HomePostsViewModel> {
            BaseVMFactory { HomePostsViewModel(savedSub, sortPostBy, repo) }
        }
        return vm
    }

    @Provides
    fun provideSlideInDownAnimator() = SlideInDownAnimator()
}
