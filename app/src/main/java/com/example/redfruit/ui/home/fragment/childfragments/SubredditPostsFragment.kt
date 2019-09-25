package com.example.redfruit.ui.home.fragment.childfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.databinding.SubredditPostsFragmentBinding
import com.example.redfruit.ui.home.adapter.PostListAdapter
import com.example.redfruit.ui.home.viewmodel.HomePostsViewModel
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.subreddit_posts_fragment.view.*
import javax.inject.Inject
import javax.inject.Provider

/**
 * Fragment that displays the posts of a subreddit
 * @property homePostsViewModel control logic of the fragment
 * @property linearLayoutManagerProvider gets new instance of LayoutManager
 * @property postListAdapter used for RecyclerView
 */
class SubredditPostsFragment : DaggerFragment() {

    @Inject
    lateinit var homePostsViewModel: HomePostsViewModel

    @Inject
    lateinit var linearLayoutManagerProvider: Provider<LinearLayoutManager>

    @Inject
    lateinit var slideInDownAnimator: SlideInDownAnimator

    @Inject
    lateinit var postListAdapter: PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // get SubredditAboutViewModel instance from the MainActivity
        val subredditAboutViewModel by activityViewModels<SubredditAboutViewModel>()
        subredditAboutViewModel.data.observe(viewLifecycleOwner, Observer { sub ->
            if (sub.display_name != homePostsViewModel.subReddit) {
                homePostsViewModel.changeSub(sub.display_name)
            }
        })

        val binding: SubredditPostsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.subreddit_posts_fragment, container, false)

        binding.apply {
            // make binding lifecycle aware
            lifecycleOwner = viewLifecycleOwner
            viewModel = homePostsViewModel
        }


        val recyclerView = binding.root.recyclerViewPosts.apply {
            layoutManager = linearLayoutManagerProvider.get()
            adapter = postListAdapter
            itemAnimator = slideInDownAnimator
            setHasFixedSize(true)
        }
        recyclerView.itemAnimator?.apply {
            addDuration = 600
            removeDuration = 600
            moveDuration = 600
            changeDuration = 600
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (homePostsViewModel.isLoading.value == false &&
                    totalItemCount == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    homePostsViewModel.isLoading.value = true
                    homePostsViewModel.loadMoreData(Constants.LIMIT)
                }
            }
        })

        binding.root.homeSwipeRefresh.apply {
            setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark),
                ContextCompat.getColor(requireContext(), R.color.colorAccent)
            )
        }

        return binding.root
    }

    companion object {
        /**
         * Provide SortBy preference
         */
        fun newInstance(sortBy: SortBy): SubredditPostsFragment {
            val fragment = SubredditPostsFragment()
            fragment.arguments = bundleOf(Constants.SORT_BY_KEY to sortBy.name)

            return fragment
        }
    }
}
