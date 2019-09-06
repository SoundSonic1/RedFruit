package com.example.redfruit.ui.home.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.databinding.FragmentHomeBinding
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.viewmodel.HomeViewModel
import com.example.redfruit.ui.shared.PostSharedViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // make binding lifecycle aware
        binding.lifecycleOwner = viewLifecycleOwner
        // Create ViewModel specifically for this Fragment
        //viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
        //    .get(HomeViewModel::class.java)
        viewModel = ViewModelProvider(
            this,
            SavedStateViewModelFactory(requireContext().applicationContext as Application, this)
        ).get(HomeViewModel::class.java)
        // get PostSharedViewModel instance from the MainActivity
        val sharedViewModel = ViewModelProvider(activity!!).get(PostSharedViewModel::class.java)

        val adapter = HomeAdapter(mutableListOf()) { post ->
            Toast.makeText(requireContext(),"Clicked on " + post.title, Toast.LENGTH_SHORT).show()
            // viewModel.update(sharedViewModel.data.value!!)
        }

        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recyclerViewHome)
        // recyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!viewModel.loading && totalItemCount == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    viewModel.loadMoreData(10)
                }
            }
        })

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getSavedData().isNotBlank()) {
            Toast.makeText(requireContext(), viewModel.getSavedData(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveData()
    }
}