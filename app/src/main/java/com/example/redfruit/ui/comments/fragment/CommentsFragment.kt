package com.example.redfruit.ui.comments.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.android.synthetic.main.comments_fragment.*
import kotlinx.android.synthetic.main.comments_fragment.view.*

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar(toolbarComments as Toolbar, activity as AppCompatActivity)

        recycler_view_comments.apply {
                adapter = groupAdapter
                layoutManager = linearLayoutManager.get()
                addItemDecoration(
                    DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
                )
            }
        recycler_view_comments.itemAnimator?.apply {
            addDuration = 400
            removeDuration = 400
            moveDuration = 400
            changeDuration = 400
        }

        if (post.post_hint == "link") {
            postUrlEmbeddeView.apply {
                visibility = View.VISIBLE
                setURL(
                    post.url, object : URLEmbeddedView.OnLoadURLListener {
                        override fun onLoadURLCompleted(data: URLEmbeddedData) {
                            postUrlEmbeddeView.setData(data)
                        }
                    }
                )
                lifecycle.addObserver(this)
            }
        }

        commentsSwipeRefresh.apply {
            setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark),
                ContextCompat.getColor(requireContext(), R.color.colorAccent)
            )
        }
    }

    private fun setUpToolbar(toolbar: Toolbar, appCompatActivity: AppCompatActivity) {

        toolbar.title = getString(R.string.comments_title)
        appCompatActivity.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { appCompatActivity.onBackPressed() }
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
