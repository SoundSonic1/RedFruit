package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.redfruit.R
import com.example.redfruit.ui.home.adapter.SortByPagerAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.view.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var sortByPagerAdapter: SortByPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val viewPagerHome = view.viewPagerHome.apply {
            adapter = sortByPagerAdapter
        }
        view.tabsSortBy.apply {
            setupWithViewPager(viewPagerHome)
        }

        return view
    }
}