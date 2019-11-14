package com.example.redfruit.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.redfruit.ui.home.fragment.childfragments.SubredditAboutFragment
import com.example.redfruit.ui.home.fragment.childfragments.SubredditPostsFragment
import javax.inject.Inject

/**
 * Show posts based on SortPostBy preference and About page of the subreddit
 */
class SubredditPagerAdapter @Inject constructor(
    val categories: MutableList<String>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    /**
     * Returns NEW fragment instance
     */
    override fun getItem(position: Int): Fragment = when (position) {
        0 -> SubredditPostsFragment()
        else -> SubredditAboutFragment()
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int) = categories[position]
}
