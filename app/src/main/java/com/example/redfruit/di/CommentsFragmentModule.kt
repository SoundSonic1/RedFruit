package com.example.redfruit.di

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.repositories.CommentsRepository
import com.example.redfruit.data.repositories.ICommentsRepository
import com.example.redfruit.ui.comments.fragment.CommentsFragment
import com.example.redfruit.ui.comments.viewmodel.CommentsViewModel
import com.example.redfruit.util.BaseVMFactory
import com.example.redfruit.util.Constants
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object CommentsFragmentModule {

    @Provides
    fun provideContext(fragment: CommentsFragment) = fragment.requireContext()

    @Provides
    @Named("SubredditName")
    fun provideSubredditName(fragment: CommentsFragment) =
        fragment.arguments?.getString(Constants.SUBREDDIT_NAME_KEY) ?: ""

    @Provides
    @Named("PostId")
    fun providePostId(fragment: CommentsFragment) =
        fragment.arguments?.getString(Constants.POST_ID_KEY) ?: ""

    @Provides
    fun provideGroupAdapter() = GroupAdapter<GroupieViewHolder>()

    @Provides
    fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

    @Provides
    fun provideCommentsRepo(
        redditApi: RedditApi,
        @Named("SubredditName") sub: String,
        @Named("PostId") id: String
    ): ICommentsRepository =
        CommentsRepository(redditApi, sub, id)

    @Provides
    fun provideCommentsVM(
        repo: ICommentsRepository,
        fragment: CommentsFragment
    ): CommentsViewModel {
        val vm by fragment.viewModels<CommentsViewModel> {
            BaseVMFactory { CommentsViewModel(repo) }
        }

        return vm
    }
}