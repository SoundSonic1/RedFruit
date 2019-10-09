package com.example.redfruit.di

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.data.api.CommentsRepository
import com.example.redfruit.data.api.ICommentsRepository
import com.example.redfruit.data.api.IRedditApi
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
class CommentsFragmentModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideContext(fragment: CommentsFragment) = fragment.requireContext()

        @Provides
        @JvmStatic
        @Named("SubredditName")
        fun provideSubredditName(fragment: CommentsFragment) =
            fragment.arguments?.getString(Constants.SUBREDDIT_NAME_KEY) ?: ""

        @Provides
        @JvmStatic
        @Named("PostId")
        fun providePostId(fragment: CommentsFragment) =
            fragment.arguments?.getString(Constants.POST_ID_KEY) ?: ""

        @Provides
        @JvmStatic
        fun provideGroupAdapter() = GroupAdapter<GroupieViewHolder>()

        @Provides
        @JvmStatic
        fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

        @Provides
        @JvmStatic
        fun provideCommentsRepo(
            redditApi: IRedditApi,
            @Named("SubredditName") sub: String,
            @Named("PostId") id: String
        ): ICommentsRepository = CommentsRepository(redditApi, sub, id)

        @Provides
        @JvmStatic
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
}