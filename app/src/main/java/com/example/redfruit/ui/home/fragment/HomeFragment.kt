package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.ui.home.adapter.SubredditPagerAdapter
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.isValidSub
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var subredditPagerAdapter: SubredditPagerAdapter

    val subredditAboutViewModel by activityViewModels<SubredditAboutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // required to create menu
        setHasOptionsMenu(true)

        val homeView = inflater.inflate(R.layout.home_fragment, container, false)
        val viewPagerHome = homeView.viewPagerHome.apply {
            adapter = subredditPagerAdapter
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
                 *  @author: https://stackoverflow.com/questions/18609261/getting-the-current-fragment-instance-in-the-viewpager
                 */
                override fun onTabReselected(tab: TabLayout.Tab) {
                    val tag = "android:switcher:" + viewPagerHome.id + ":" + viewPagerHome.currentItem
                    val fragment = childFragmentManager.findFragmentByTag(tag)
                    fragment?.let {
                       it.view?.findViewById<RecyclerView>(R.id.recyclerViewPosts)?.apply {
                           this.layoutManager?.smoothScrollToPosition(this, RecyclerView.State(), 0)
                       }
                    }
                }
            }
        )

        return homeView
    }

    /**
     * Create search view to search for subreddits
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.action_search_subreddit)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (subredditAboutViewModel.data.value?.display_name?.toLowerCase(Locale.ENGLISH)
                    != query.toLowerCase(Locale.ENGLISH)) {
                    changeSubIfValid(query)
                }
                // collapse menu item
                searchItem.collapseActionView()
                return true
            }

        })
    }

    /**
     * Checks whether the requested sub from the user exists
     */
    private fun changeSubIfValid(sub: String) {
        CoroutineScope(Dispatchers.Main).launch {
            if (isValidSub(sub)) {
                // set new subreddit
                subredditAboutViewModel.setSub(sub)
            } else {
                Toast.makeText(
                    requireContext(),
                    "The subreddit $sub could not be found.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}