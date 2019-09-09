package com.example.redfruit.ui.home.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.databinding.FragmentHomeBinding
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.viewmodel.HomeViewModel
import com.example.redfruit.ui.shared.SubredditViewModel

/**
 * Main fragment that displays the posts in a subreddit
 */
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            SavedStateViewModelFactory(requireContext().applicationContext as Application, this)
        ).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.apply {
            // make binding lifecycle aware
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
        }

        // get SubredditViewModel instance from the MainActivity
        val sharedViewModel = ViewModelProvider(activity!!).get(SubredditViewModel::class.java)
        sharedViewModel.data.observe(viewLifecycleOwner, Observer {
            if (it.isNotBlank()) {
                homeViewModel.changeSub(it)
                changeAppTitle(homeViewModel.subReddit)
            }
        })

        val homeAdapter = HomeAdapter(mutableListOf()) { post ->
            Toast.makeText(requireContext(),
                "Clicked on ${post.title}", Toast.LENGTH_SHORT).show()
            // viewModel.update(sharedViewModel.data.value!!)
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
                    homeViewModel.loadMoreData(10)
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (homeViewModel.getSavedData().isNotBlank()) {
            Toast.makeText(requireContext(), homeViewModel.getSavedData(), Toast.LENGTH_SHORT).show()
        }
        changeAppTitle(homeViewModel.subReddit)
    }

    override fun onPause() {
        super.onPause()
        homeViewModel.saveData()
    }

    private fun changeAppTitle(name: String) {
        (activity as AppCompatActivity).supportActionBar?.title = name
    }
}