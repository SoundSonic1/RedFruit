package com.example.redfruit.ui.comments.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.CommentsFragmentBinding
import com.example.redfruit.ui.comments.viewmodel.CommentsViewModel
import com.example.redfruit.ui.shared.OnPostClickHandlerImpl
import com.example.redfruit.util.Constants
import com.nguyencse.URLEmbeddedData
import com.nguyencse.URLEmbeddedView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.android.synthetic.main.comments_fragment.view.commentsSwipeRefresh
import kotlinx.android.synthetic.main.comments_fragment.view.postUrlEmbeddeView

/**
 * Displays the detailed post and the comment section
 */
class CommentsFragment : DaggerFragment() {

    @Inject
    lateinit var post: Post

    @Inject
    lateinit var viewModel: CommentsViewModel

    @Inject
    lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    @Inject
    lateinit var linearLayoutManager: Provider<LinearLayoutManager>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CommentsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.comments_fragment, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.post = post
        binding.viewModel = viewModel
        binding.onImageClickHandler = OnPostClickHandlerImpl(activity!!.supportFragmentManager)

        val recyclerView =
            binding.root.findViewById<RecyclerView>(R.id.recycler_view_comments).apply {
                adapter = groupAdapter
                layoutManager = linearLayoutManager.get()
                addItemDecoration(
                    DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
                )
            }

        recyclerView.itemAnimator?.apply {
            addDuration = 400
            removeDuration = 400
            moveDuration = 400
            changeDuration = 400
        }

        binding.root.commentsSwipeRefresh.apply {
            setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark),
                ContextCompat.getColor(requireContext(), R.color.colorAccent)
            )
        }

        if (post.post_hint == "link") {
            binding.root.postUrlEmbeddeView.apply {
                visibility = View.VISIBLE
                setURL(
                    post.url, object : URLEmbeddedView.OnLoadURLListener {
                        override fun onLoadURLCompleted(data: URLEmbeddedData) {
                            binding.root.postUrlEmbeddeView.setData(data)
                        }
                    }
                )
                lifecycle.addObserver(this)
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance(post: Post): CommentsFragment {

            return CommentsFragment().apply {
                arguments = bundleOf(
                    Constants.POSTDETAIL_KEY to post
                )
            }
        }
    }
}
