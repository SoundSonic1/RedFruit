package com.example.redfruit.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.redfruit.R
import com.example.redfruit.ui.home.fragment.HomeFragment
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants.DARK_THEME_ON_KEY
import com.example.redfruit.util.Constants.HOME_FRAGMENT_TAG
import com.example.redfruit.util.replaceFragmentIgnoreBackstack
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.activity_main.switch_compat

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

        setContentView(R.layout.activity_main)

        nav_view.setNavigationItemSelectedListener(this)

        if (getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            switch_compat.isChecked = true
        }

        switch_compat.setOnCheckedChangeListener { _, b ->
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPref.edit {
                    putInt(DARK_THEME_ON_KEY, AppCompatDelegate.MODE_NIGHT_YES)
                }
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPref.edit {
                    putInt(DARK_THEME_ON_KEY, AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        // Start with home fragment
        if (savedInstanceState == null) {
            replaceFragmentIgnoreBackstack(
                supportFragmentManager,
                R.id.mainContent,
                HomeFragment(),
                HOME_FRAGMENT_TAG
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
