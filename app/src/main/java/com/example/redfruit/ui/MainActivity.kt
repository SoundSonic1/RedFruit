package com.example.redfruit.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.redfruit.R
import com.example.redfruit.databinding.ActivityMainBinding
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants
import com.example.redfruit.util.findOrCreateFragment
import com.example.redfruit.util.isValidSub
import com.example.redfruit.util.replaceFragmentIgnoreBackstack
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Provider


/**
 * Entry point of our app. We use the single Activity, many fragments philosophy.
 * The app moves towards clean architecture in the form of MVVM and Repository pattern
 * @property homeFragment activity starts with this fragment
 * @property subredditAboutViewModel is a shared viewmodel to communicate between activity
 * and fragments
 */
class MainActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var homeFragmentProvider: Provider<HomeFragment>

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var subredditAboutViewModel: SubredditAboutViewModel

    lateinit var homeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModelMainactivity = subredditAboutViewModel

        collapsingToolbarLayout.isTitleEnabled = false
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        subredditAboutViewModel.data.observe(this, Observer {
            // TODO: change title via data binding
            supportActionBar?.title = it.display_name
            // save latest subreddit
            sharedPref.edit { putString(getString(R.string.saved_subreddit), it.display_name) }
        })

        homeFragment = findOrCreateFragment(supportFragmentManager, Constants.HOME_FRAGMENT_TAG) {
            homeFragmentProvider.get()
        }

        // Start with home fragment
        if (savedInstanceState == null) {
            replaceFragmentIgnoreBackstack(
                supportFragmentManager,
                R.id.mainContent,
                homeFragment,
                Constants.HOME_FRAGMENT_TAG
            )
        }
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
                if (subredditAboutViewModel.data.value?.display_name?.toLowerCase(Locale.ENGLISH)
                    != query.toLowerCase(Locale.ENGLISH)) {
                    changeSubIfValid(query)
                }
                // collapse menu item
                searchItem.collapseActionView()
                return true
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
        CoroutineScope(Dispatchers.Main).launch {
            if (isValidSub(sub)) {
                // set new subreddit
                subredditAboutViewModel.setSub(sub)
                /*addOrShowFragment(
                    supportFragmentManager,
                    R.id.mainContent,
                    homeFragment,
                    Constants.HOME_FRAGMENT_TAG
                )*/
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "The subreddit $sub could not be found.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}