package com.example.redfruit.di

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.repositories.CommentsRepository
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
    fun providePost(fragment: CommentsFragment): Post =
        fragment.arguments!!.getParcelable(Constants.POSTDETAIL_KEY)!!

    @Provides
    @Named("SubredditName")
    fun provideSubredditName(post: Post) = post.subreddit

    @Provides
    @Named("PostId")
    fun providePostId(post: Post) = post.id

    @Provides
    fun provideGroupAdapter() = GroupAdapter<GroupieViewHolder>()

    @Provides
    fun provideLinearLayoutManager(context: Context) = LinearLayoutManager(context)

    @Provides
    fun provideCommentsVM(
        repo: CommentsRepository,
        fragment: CommentsFragment
    ): CommentsViewModel {
        val vm by fragment.viewModels<CommentsViewModel> {
            BaseVMFactory { CommentsViewModel(repo) }
        }

        return vm
    }
}
