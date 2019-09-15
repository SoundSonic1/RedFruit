package com.example.redfruit.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.redfruit.data.model.enumeration.SortBy

/**
 * Adapter to show fragments based on SortBy for ViewPager
 */
class SortByPagerAdapter(
    private val fragments: List<Fragment>,
    private val categories: List<SortBy>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = categories[position].name

}