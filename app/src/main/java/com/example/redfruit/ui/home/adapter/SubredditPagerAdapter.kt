package com.example.redfruit.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.home.fragment.childfragments.SubredditAboutFragment
import com.example.redfruit.ui.home.fragment.childfragments.SubredditPostsFragment

/**
 * Show posts based on SortBy preference and About page of the subreddit
 */
class SubredditPagerAdapter(
    private val categories: List<String>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    /**
     * Returns NEW fragment instance
     */
    override fun getItem(position: Int): Fragment = when(position) {
        0 -> SubredditPostsFragment.newInstance(SortBy.hot)
        1 -> SubredditPostsFragment.newInstance(SortBy.new)
        else -> SubredditAboutFragment()
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int) = categories[position]

}