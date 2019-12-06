package com.example.redfruit.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.example.redfruit.R
import com.example.redfruit.databinding.ActivityMainBinding
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants
import com.example.redfruit.util.replaceFragmentIgnoreBackstack
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.activity_main.switch_compat
import kotlinx.android.synthetic.main.app_bar_main.collapsingToolbarLayout

/**
 * Entry point of our app. We use the single Activity, many fragments philosophy.
 * The app moves towards clean architecture in the form of MVVM and Repository pattern
 *
 */
class MainActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var subredditAboutViewModel: SubredditAboutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModelMainactivity = subredditAboutViewModel

        collapsingToolbarLayout.isTitleEnabled = false
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        switch_compat.isChecked = sharedPref.getBoolean(Constants.SWITCH_TOGGLE_KEY, false)

        AppCompatDelegate.setDefaultNightMode(
            sharedPref.getInt(Constants.DARK_THEME_ON_KEY, AppCompatDelegate.MODE_NIGHT_NO)
        )

        switch_compat.setOnCheckedChangeListener { _, b ->
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPref.edit {
                    putBoolean(Constants.SWITCH_TOGGLE_KEY, b)
                    putInt(Constants.DARK_THEME_ON_KEY, AppCompatDelegate.MODE_NIGHT_YES)
                }
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPref.edit {
                    putBoolean(Constants.SWITCH_TOGGLE_KEY, b)
                    putInt(Constants.DARK_THEME_ON_KEY, AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        subredditAboutViewModel.data.observe(this, Observer {
            // TODO: change title via data binding
            supportActionBar?.title = it.display_name
            // save latest subreddit
            sharedPref.edit { putString(getString(R.string.saved_subreddit), it.display_name) }
        })

        // Start with home fragment
        if (savedInstanceState == null) {
            replaceFragmentIgnoreBackstack(
                supportFragmentManager,
                R.id.mainContent,
                HomeFragment(),
                Constants.HOME_FRAGMENT_TAG
            )
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true) // show back button
                toolbar.setNavigationOnClickListener { onBackPressed() }
            } else {
                // show hamburger
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toggle.syncState()
                toolbar.setNavigationOnClickListener {
                    drawer_layout.openDrawer(GravityCompat.START)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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
}
