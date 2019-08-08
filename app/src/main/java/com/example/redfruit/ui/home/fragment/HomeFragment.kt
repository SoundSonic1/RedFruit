package com.example.redfruit.ui.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.ui.home.HomeAdapter
import com.example.redfruit.ui.home.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val activity = activity as Context
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        val adapter = HomeAdapter(){ }
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHome)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        viewModel.getData().observe(this, Observer {
            adapter.refreshData(it)
        })

        return view
    }
}