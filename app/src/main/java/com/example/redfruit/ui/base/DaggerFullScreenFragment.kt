package com.example.redfruit.ui.base

import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerFragment

abstract class DaggerFullScreenFragment : DaggerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideSystemUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
