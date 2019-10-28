package com.example.redfruit.ui.base

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import com.example.redfruit.R
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.DaggerFragment

abstract class DaggerFullScreenFragment : DaggerFragment() {
// TODO: hide navigation bar

    private var initToolbarHeight: Int = 0

    private fun disableToolbar(fragmentActivity: FragmentActivity) {
        val appbarLayout = fragmentActivity.findViewById<AppBarLayout>(R.id.appBarLayout)
        val lp = appbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        initToolbarHeight = lp.height
        lp.height = 0
        appbarLayout.layoutParams = lp
    }

    private fun enableToolbar(fragmentActivity: FragmentActivity) {
        val appbarLayout = fragmentActivity.findViewById<AppBarLayout>(R.id.appBarLayout)
        val lp = appbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = initToolbarHeight
        appbarLayout.layoutParams = lp
    }

    override fun onResume() {
        super.onResume()
        disableToolbar(activity!!)
        hideSystemUI()
    }

    override fun onPause() {
        super.onPause()
        enableToolbar(activity!!)
        showSystemUI()
    }

    private fun showSystemUI() {
        activity?.window?.decorView.apply {
            this?.let {
                systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }

    private fun hideSystemUI() {
        activity?.window?.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            this?.let {
                systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
        }
    }
}