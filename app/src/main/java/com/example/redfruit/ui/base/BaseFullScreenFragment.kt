package com.example.redfruit.ui.base

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.redfruit.R
import com.google.android.material.appbar.AppBarLayout

/**
 * Abstract class which implements functions to enable/disable the toolbar in MainActivity
 */
abstract class BaseFullScreenFragment : Fragment() {
// TODO: hide navigation bar

    private var initToolbarHeight: Int = 0

    protected fun disableToolbar(fragmentActivity: FragmentActivity) {
        val appbarLayout = fragmentActivity.findViewById<AppBarLayout>(R.id.appBarLayout)
        val lp = appbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        initToolbarHeight = lp.height
        lp.height = 0
        appbarLayout.layoutParams = lp
    }

    protected fun enableToolbar(fragmentActivity: FragmentActivity) {
        val appbarLayout = fragmentActivity.findViewById<AppBarLayout>(R.id.appBarLayout)
        val lp = appbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = initToolbarHeight
        appbarLayout.layoutParams = lp
    }
}