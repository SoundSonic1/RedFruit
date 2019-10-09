package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.ui.home.adapter.SubredditPagerAdapter
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.view.*
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
     * Create menu for home section
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home, menu)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.sortMenu -> {
                showPopUpSortMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopUpSortMenu() {
        val item = activity?.findViewById<View>(R.id.sortMenu)
        item?.let{
            val menu = PopupMenu(requireContext(), it).apply {
                inflate(R.menu.sortby_menu)
                show()
            }
            menu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.sortByHot -> {
                        subredditAboutViewModel.setSort(SortBy.hot)
                        changeSortByTitle(SortBy.hot)
                        true
                    }
                    R.id.sortByNew -> {
                        subredditAboutViewModel.setSort(SortBy.new)
                        changeSortByTitle(SortBy.new)
                        true
                    }
                    R.id.sortByTop -> {
                        subredditAboutViewModel.setSort(SortBy.top)
                        changeSortByTitle(SortBy.top)
                        true
                    }
                    R.id.sortByRising -> {
                        subredditAboutViewModel.setSort(SortBy.rising)
                        changeSortByTitle(SortBy.rising)
                        true
                    }
                    R.id.sortByControversial -> {
                        subredditAboutViewModel.setSort(SortBy.controversial)
                        changeSortByTitle(SortBy.controversial)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun changeSortByTitle(sortBy: SortBy) {
        subredditPagerAdapter.categories[0] = sortBy.name
        subredditPagerAdapter.notifyDataSetChanged()
    }

    /**
     * Checks whether the requested sub from the user exists
     */
    private fun changeSubIfValid(sub: String) {
        lifecycleScope.launch {
            if (!subredditAboutViewModel.setSub(sub)) {
                Toast.makeText(
                    requireContext(),
                    "The subreddit $sub could not be found.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}