package com.example.redfruit.ui.base

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.redfruit.R
import com.google.android.material.appbar.AppBarLayout

/**
 * Abstract class which implements functions to enable/disable the toolbar in MainActivity
 */
abstract class FullScreenFragment : Fragment() {
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
    }

    override fun onPause() {
        super.onPause()
        enableToolbar(activity!!)
    }
}