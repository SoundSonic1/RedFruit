package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.api.SubRedditRepository
import com.example.redfruit.databinding.FragmentHomeBinding
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.viewmodel.HomeVMFactory
import com.example.redfruit.ui.home.viewmodel.HomeViewModel
import com.example.redfruit.ui.shared.SubredditViewModel
import com.example.redfruit.util.Constants

/**
 * Main fragment that displays the posts in a subreddit
 * @property repo used to fetch reddit posts for a subreddit
 * @property homeViewModel control logic of the fragment
 */
class HomeFragment : Fragment() {

    private val repo by lazy {
        SubRedditRepository()
    }

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // get SubredditViewModel instance from the MainActivity
        val sharedViewModel = ViewModelProvider(activity!!).get(SubredditViewModel::class.java)

        homeViewModel = ViewModelProvider(
            this,
            HomeVMFactory(sharedViewModel.data.value ?: Constants.DEFAULT_SUB, repo)
        ).get(HomeViewModel::class.java)

        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.apply {
            // make binding lifecycle aware
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
        }

        sharedViewModel.data.observe(viewLifecycleOwner, Observer {
            if (it != homeViewModel.subReddit) {
                homeViewModel.changeSub(it)
            }
        })

        val homeAdapter = HomeAdapter(mutableListOf()) { post ->
            Toast.makeText(requireContext(),
                "Clicked on ${post.title}", Toast.LENGTH_SHORT).show()
        }

        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recyclerViewHome).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!homeViewModel.loading &&
                    totalItemCount == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    homeViewModel.loading = true
                    homeViewModel.loadMoreData(Constants.LIMIT)
                }
            }
        })

        return binding.root
    }
}