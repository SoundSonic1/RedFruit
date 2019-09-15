package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.ui.home.adapter.SortByPagerAdapter
import com.google.android.material.tabs.TabLayout
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
        val homeView = inflater.inflate(R.layout.home_fragment, container, false)
        val viewPagerHome = homeView.viewPagerHome.apply {
            adapter = sortByPagerAdapter
        }
        homeView.tabsSortBy.apply {
            setupWithViewPager(viewPagerHome)
        }

        homeView.tabsSortBy.addOnTabSelectedListener(
            object : TabLayout.ViewPagerOnTabSelectedListener(viewPagerHome) {
                override fun onTabSelected(tab: TabLayout.Tab) = Unit

                override fun onTabUnselected(tab: TabLayout.Tab) = Unit

                /**
                 *  Scroll to top when user "double taps" the tab
                 */
                override fun onTabReselected(tab: TabLayout.Tab) {
                    sortByPagerAdapter.getItem(tab.position).apply {
                        this.view?.findViewById<RecyclerView>(R.id.recyclerViewHomeSortBy).apply {
                            this?.layoutManager?.smoothScrollToPosition(this, RecyclerView.State(), 0)
                        }
                    }
                }
            }
        )

        return homeView
    }
}