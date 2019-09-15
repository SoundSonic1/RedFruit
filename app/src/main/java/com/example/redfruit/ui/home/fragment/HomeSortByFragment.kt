package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.databinding.HomeSortByFragmentBinding
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.viewmodel.HomeViewModel
import com.example.redfruit.ui.shared.SubredditViewModel
import com.example.redfruit.util.Constants
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_sort_by_fragment.view.*
import javax.inject.Inject
import javax.inject.Provider

/**
 * Fragment that displays the posts of a subreddit
 * @property homeViewModel control logic of the fragment
 * @property linearLayoutManagerProvider gets new instance of LayoutManager
 * @property homeAdapter used for RecyclerView
 */
class HomeSortByFragment : DaggerFragment() {

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var linearLayoutManagerProvider: Provider<LinearLayoutManager>

    @Inject
    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // get SubredditViewModel instance from the MainActivity
        activity?.let {
            val sharedViewModel = ViewModelProvider(it).get(SubredditViewModel::class.java)
            sharedViewModel.data.observe(viewLifecycleOwner, Observer {
                if (it != homeViewModel.subReddit) {
                    homeViewModel.changeSub(it)
                }
            })
        }

        val binding: HomeSortByFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.home_sort_by_fragment, container, false)

        binding.apply {
            // make binding lifecycle aware
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
        }


        val recyclerView = binding.root.recyclerViewHomeSortBy.apply {
            layoutManager = linearLayoutManagerProvider.get()
            adapter = homeAdapter
            setHasFixedSize(true)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (homeViewModel.isLoading.value == false &&
                    totalItemCount == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    homeViewModel.isLoading.value = true
                    homeViewModel.loadMoreData(Constants.LIMIT)
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
        fun newInstance(sortBy: SortBy): HomeSortByFragment {
            val fragment = HomeSortByFragment()
            val args = Bundle().apply {
                putString(Constants.SORT_BY_KEY, sortBy.toString())
            }
            fragment.arguments = args
            return fragment
        }
    }
}
