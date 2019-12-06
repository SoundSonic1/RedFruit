package com.example.redfruit.ui.home.fragment

import android.content.SharedPreferences
import android.database.MatrixCursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.content.edit
import androidx.cursoradapter.widget.CursorAdapter
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.enumeration.SortPostBy
import com.example.redfruit.databinding.HomeFragmentBinding
import com.example.redfruit.ui.home.adapter.SubredditPagerAdapter
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import java.util.Locale
import javax.inject.Inject
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.coroutines.launch

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var subredditPagerAdapter: SubredditPagerAdapter

    @Inject
    lateinit var cursorAdapter: CursorAdapter

    private val subredditAboutViewModel by activityViewModels<SubredditAboutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: HomeFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = subredditAboutViewModel

        val toolbar = binding.root.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        val drawerLayout = activity!!.findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            activity, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val viewPagerHome = binding.root.viewPagerHome.apply {
            adapter = subredditPagerAdapter
        }
        binding.root.tabsSortBy.setupWithViewPager(viewPagerHome)

        binding.root.tabsSortBy.addOnTabSelectedListener(
            object : TabLayout.ViewPagerOnTabSelectedListener(viewPagerHome) {

                override fun onTabSelected(tab: TabLayout.Tab) = Unit

                override fun onTabUnselected(tab: TabLayout.Tab) = Unit

                /**
                 *  Scroll to top when user "double taps" the tab
                 *  @author: https://stackoverflow.com/questions/18609261/getting-the-current-fragment-instance-in-the-viewpager
                 */
                override fun onTabReselected(tab: TabLayout.Tab) {
                    val tag = "android:switcher:${viewPagerHome.id}:${viewPagerHome.currentItem}"
                    val fragment = childFragmentManager.findFragmentByTag(tag)
                    fragment?.let {
                        it.view?.findViewById<RecyclerView>(R.id.recyclerViewPosts)?.apply {
                            this.layoutManager?.smoothScrollToPosition(
                                this, RecyclerView.State(), 0
                            )
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

        binding.root.collapsingToolbarLayout.isTitleEnabled = false
        subredditAboutViewModel.data.observe(this, Observer {
            toolbar.title = it.display_name
            sharedPref.edit { putString(getString(R.string.saved_subreddit), it.display_name) }
        })

        return binding.root
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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortMenu -> {
                showPopUpSortMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopUpSortMenu() {
        val itemView = activity?.findViewById<View>(R.id.sortMenu)
        itemView?.let { item ->
            val menu = PopupMenu(requireContext(), item).apply {
                inflate(R.menu.sortby_menu)
                show()
            }
            menu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.sortByHot -> {
                        subredditAboutViewModel.setSort(SortPostBy.hot)
                        changeSortByTitle(SortPostBy.hot)
                        true
                    }
                    R.id.sortByNew -> {
                        subredditAboutViewModel.setSort(SortPostBy.new)
                        changeSortByTitle(SortPostBy.new)
                        true
                    }
                    R.id.sortByTop -> {
                        subredditAboutViewModel.setSort(SortPostBy.top)
                        changeSortByTitle(SortPostBy.top)
                        true
                    }
                    R.id.sortByRising -> {
                        subredditAboutViewModel.setSort(SortPostBy.rising)
                        changeSortByTitle(SortPostBy.rising)
                        true
                    }
                    R.id.sortByControversial -> {
                        subredditAboutViewModel.setSort(SortPostBy.controversial)
                        changeSortByTitle(SortPostBy.controversial)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun changeSortByTitle(sortPostBy: SortPostBy) = subredditPagerAdapter.apply {
        categories[0] = sortPostBy.name
        notifyDataSetChanged()
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
