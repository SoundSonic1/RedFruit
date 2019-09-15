package com.example.redfruit.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.home.fragment.HomeSortByFragment

class SortByPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> HomeSortByFragment.newInstance(SortBy.hot)
            else -> HomeSortByFragment.newInstance(SortBy.new)
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> SortBy.hot.name
            else -> SortBy.new.name
        }
    }

}