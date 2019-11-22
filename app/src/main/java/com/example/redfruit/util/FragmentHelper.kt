package com.example.redfruit.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.redfruit.R

/**
 * @author https://thuytrinh.github.io/android/kotlin/2017/02/07/find-fragments.html
 * @param fm FragmentManager of the current activity or fragment
 * @param tag is a string used to identify the fragment
 * @return Fragment: returns either a fragment found in the FragmentManager
 * or from a higher order function
 */
fun findOrCreateFragment(fm: FragmentManager, tag: String, ifNone: (String) -> Fragment): Fragment {
    return fm.findFragmentByTag(tag) ?: ifNone(tag)
}

/**
 * Replaces container with the given fragment but does not add it to backstack
 */
fun replaceFragmentIgnoreBackstack(
    fm: FragmentManager,
    containerViewId: Int,
    fragment: Fragment,
    tag: String? = null
) {
    val transaction = fm.beginTransaction()
    transaction
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        .replace(containerViewId, fragment, tag)
        .commit()
}

/**
 * Adds/Shows the given fragment and hides all other fragments in the container
 *
 * @author https://stackoverflow.com/questions/42795611/fragmenttransaction-hide-show-doesnt-work-sometimes
 */
fun addOrShowFragment(
    fm: FragmentManager,
    containerViewId: Int,
    fragment: Fragment,
    tag: String? = null
) {
    fm.beginTransaction().apply {

        setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)

        if (fragment.isAdded) {
            show(fragment)
        } else {
            add(containerViewId, fragment, tag)
        }

        fm.fragments.forEach {
            if (it != fragment && it.isVisible) {
                hide(it)
            }
        }

        addToBackStack(null)
    }.commit()
}
