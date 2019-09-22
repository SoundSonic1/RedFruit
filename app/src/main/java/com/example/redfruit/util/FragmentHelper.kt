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
    tag: String
) {
    val transaction = fm.beginTransaction()
    transaction
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        .replace(containerViewId, fragment, tag)
        .commit()
}

/**
 * Replaces container with the given fragment.
 */
fun replaceFragment(fm: FragmentManager, containerViewId: Int, fragment: Fragment, tag: String) {
    val transaction = fm.beginTransaction()
    transaction
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        .replace(containerViewId, fragment, tag)
        .addToBackStack(null)
        .commit()
}

/**
 * Adds the given fragment to the container and puts it to the backstack.
 */
fun addFragment(
    fm: FragmentManager,
    containerViewId: Int,
    fragment: Fragment, tag: String
): Boolean {

    if (fragment.isAdded) return false

    val transaction = fm.beginTransaction()
    transaction
        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        .add(containerViewId, fragment, tag)
        .addToBackStack(null)
        .commit()

    return true
}

/**
 * Adds/Shows fragment in container and hides all other fragments
 *
 * @author https://stackoverflow.com/questions/42795611/fragmenttransaction-hide-show-doesnt-work-sometimes
 */
fun addOrShowFragment(
    fm: FragmentManager,
    containerViewId: Int,
    fragment: Fragment,
    tag: String
) {
    fm.beginTransaction().apply {

        setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)

        if (fragment.isAdded) {
            show(fragment)
        } else {
            add(containerViewId, fragment, tag)
        }

        fm.fragments.forEach {
            if (it != fragment && it.isAdded) {
                hide(it)
            }
        }

        addToBackStack(null)
    }.commit()
}
