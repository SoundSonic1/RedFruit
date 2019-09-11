package com.example.redfruit.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.redfruit.R
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.shared.SubredditVMFactory
import com.example.redfruit.ui.shared.SubredditViewModel
import com.example.redfruit.util.Constants
import com.example.redfruit.util.isValidSubDetail
import com.example.redfruit.util.replaceFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


/**
 * Entry point of our app. We use the single Activity, many fragments philosophy.
 * The app moves towards clean architecture in the form of MVVM and Repository pattern
 * @property homeFragment activity starts with this fragment
 * @property subredditViewModel is a shared viewmodel to communicate between activity
 * and fragments
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val homeFragment: HomeFragment by lazy { HomeFragment() }

    private lateinit var subredditViewModel: SubredditViewModel

    private val uiScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main)
    }

    private val sharedPref by lazy { getPreferences(Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val savedSub = sharedPref.getString(
            getString(R.string.saved_subreddit), Constants.defautSub
        ) ?: Constants.defautSub

        subredditViewModel = ViewModelProvider(
            this, SubredditVMFactory(savedSub)
        ).get(SubredditViewModel::class.java)

        // TODO: change title via data binding
        supportActionBar?.title = savedSub

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        subredditViewModel.data.observe(this, Observer {
            // save latest subreddit
            with (sharedPref.edit()) {
                putString(getString(R.string.saved_subreddit), it.toLowerCase(Locale.ENGLISH))
                commit()
            }
        })

        // Start with Home
        replaceFragment(R.id.mainContent, homeFragment)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search_subreddit)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotBlank()) {
                    changeSubIfValid(query)
                    // collapse menu item
                    searchItem.collapseActionView()
                    return true
                }
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                replaceFragment(R.id.mainContent, homeFragment, mainTag)
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Checks whether the requested sub from the user exists
     */
    private fun changeSubIfValid(sub: String) {
        uiScope.launch {
            if (isValidSubDetail(sub)) {
                // set new subreddit
                subredditViewModel.setSub(sub)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "The subreddit $sub could not be found.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        // unique tags
        private const val mainTag = "Home"
    }
}