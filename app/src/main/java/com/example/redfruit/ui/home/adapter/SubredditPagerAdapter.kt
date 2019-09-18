package com.example.redfruit.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Show posts based on SortBy preference and About page of the subreddit
 */
class SubredditPagerAdapter(
    private val fragments: List<Fragment>,
    private val categories: List<String>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = categories[position]

}