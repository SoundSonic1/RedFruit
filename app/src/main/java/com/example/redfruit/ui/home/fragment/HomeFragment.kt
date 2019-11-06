package com.example.redfruit.ui.home.fragment

import android.database.MatrixCursor
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.ui.home.adapter.SubredditPagerAdapter
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var subredditPagerAdapter: SubredditPagerAdapter

    @Inject
    lateinit var cursorAdapter: CursorAdapter

    @Inject
    lateinit var repo: SubredditAboutRepository

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

        subredditAboutViewModel.subreddits.observe(viewLifecycleOwner, Observer {
            val cursor = MatrixCursor(arrayOf("_id", Constants.SUGGESTIONS))
            it.forEach { sub ->
                if (sub.subreddit_type != "private") {
                    cursor.newRow().add(Constants.SUGGESTIONS, sub.display_name)
                }
            }
            cursorAdapter.changeCursor(cursor)
            cursorAdapter.notifyDataSetChanged()
        })

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

        searchView.apply {
            queryHint = getString(R.string.search_hint)
            suggestionsAdapter = cursorAdapter
        }

        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText == null || newText.length < 4) return false

                subredditAboutViewModel.findSubreddits(newText)

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query == null) return false

                if (subredditAboutViewModel.data.value?.display_name?.toLowerCase(Locale.ENGLISH)
                    != query.toLowerCase(Locale.ENGLISH)) {
                    changeSubIfValid(query)
                }
                // collapse menu item
                searchItem.collapseActionView()
                return true
            }

        })

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {

            override fun onSuggestionSelect(position: Int) = false

            override fun onSuggestionClick(position: Int): Boolean {

                searchItem.collapseActionView()

                return subredditAboutViewModel.changeSubOnClick(position)
            }
        }

        )
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