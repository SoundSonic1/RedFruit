package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.ui.home.viewmodel.HomeViewModel
import com.example.redfruit.ui.shared.PostSharedViewModel


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        // Create ViewModel specifically for this Fragment
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(HomeViewModel::class.java)
        // get PostSharedViewModel instance from the MainActivity
        val sharedViewModel = ViewModelProvider(activity!!).get(PostSharedViewModel::class.java)

        val adapter = HomeAdapter(mutableListOf(), requireContext()) { post ->
            Toast.makeText(requireContext(),"Clicked on " + post.title, Toast.LENGTH_SHORT).show()
            viewModel.update(sharedViewModel.data.value!!)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHome)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        // register observer via special lifecycle returned by viewLifecycleOwner
        viewModel.data.observe(viewLifecycleOwner, Observer { posts ->
            if (posts != null) {
                adapter.refreshItems(posts)
            }
        })

        return view
    }
}