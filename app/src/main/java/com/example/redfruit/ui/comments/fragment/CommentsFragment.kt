package com.example.redfruit.ui.comments.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.CommentsFragmentBinding
import com.example.redfruit.ui.comments.viewmodel.CommentsViewModel
import com.example.redfruit.util.Constants
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Provider

/**
 * Fragments that displays the post details including the comments of user
 */
class CommentsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: CommentsViewModel

    @Inject
    lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    @Inject
    lateinit var linearLayoutManager: Provider<LinearLayoutManager>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CommentsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.comments_fragment, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recycler_view_comments).apply {
            adapter = groupAdapter
            layoutManager = linearLayoutManager.get()
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )
            setHasFixedSize(true)
        }

        recyclerView.itemAnimator?.apply {
            addDuration = 400
            removeDuration = 400
            moveDuration = 400
            changeDuration = 400
        }

        return binding.root
    }

    companion object {
        fun newInstance(post: Post): CommentsFragment {

            return CommentsFragment().apply {
                arguments = bundleOf(
                    Constants.SUBREDDIT_NAME_KEY to post.subreddit,
                    Constants.POST_ID_KEY to post.id
                )
            }
        }
    }

}
