package com.example.redfruit.ui.home.fragment.childfragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.databinding.SubredditPostsFragmentBinding
import com.example.redfruit.ui.home.adapter.PostListAdapter
import com.example.redfruit.ui.home.viewmodel.HomePostsViewModel
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import kotlinx.android.synthetic.main.subreddit_posts_fragment.*
import kotlinx.android.synthetic.main.subreddit_posts_fragment.view.homeSwipeRefresh
import kotlinx.android.synthetic.main.subreddit_posts_fragment.view.recyclerViewPosts

/**
 * Fragment that displays the posts of a subreddit
*/
class SubredditPostsFragment : DaggerFragment() {

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var homePostsViewModel: HomePostsViewModel

    @Inject
    lateinit var linearLayoutManagerProvider: Provider<LinearLayoutManager>

    @Inject
    @field:Named("ItemAnimator")
    lateinit var customItemAnimator: RecyclerView.ItemAnimator

    @Inject
    lateinit var postListAdapter: PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val subredditAboutViewModel by activityViewModels<SubredditAboutViewModel>()

        subredditAboutViewModel.data.observe(viewLifecycleOwner, Observer { sub ->
            if (sub.display_name != homePostsViewModel.subReddit) {
                homePostsViewModel.changeSub(sub.display_name)
            }
        })

        subredditAboutViewModel.sortPostBy.observe(viewLifecycleOwner, Observer {
            if (it != homePostsViewModel.sortPostBy) {
                sharedPref.edit {
                    putString(Constants.SORTBY_SHARED_KEY, it.name)
                }
                postListAdapter.submitList(listOf())
                homePostsViewModel.sortPostBy = it
                homePostsViewModel.loadMoreData(Constants.LIMIT)
            }
        })

        val binding: SubredditPostsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.subreddit_posts_fragment, container, false)

        binding.apply {
            // make binding lifecycle aware
            lifecycleOwner = viewLifecycleOwner
            viewModel = homePostsViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewPosts.apply {
            layoutManager = linearLayoutManagerProvider.get()
            adapter = postListAdapter
            itemAnimator = customItemAnimator
        }
        recyclerViewPosts.itemAnimator?.apply {
            addDuration = 600
            removeDuration = 600
            moveDuration = 600
            changeDuration = 600
        }
        recyclerViewPosts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        homeSwipeRefresh.apply {
            setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark),
                ContextCompat.getColor(requireContext(), R.color.colorAccent)
            )
        }
    }
}
