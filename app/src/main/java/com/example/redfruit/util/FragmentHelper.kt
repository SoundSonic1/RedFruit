package com.example.redfruit.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * @author https://thuytrinh.github.io/android/kotlin/2017/02/07/find-fragments.html
 * @param fm FragmentManager of the current activity or fragment
 * @param tag is a string used to identify the fragment
 * @return Fragment: returns either a fragment found in the FragmentManager
 * or from a higher order function
 */
fun findFragmentByTag(fm: FragmentManager, tag: String, ifNone: (String) -> Fragment): Fragment {
    return fm.findFragmentByTag(tag) ?: ifNone(tag)
}

/**
 * Replaces container with a given fragment
 * @param fm FragmentManager of the current activity or fragment
 * @param containerViewId is the id of the frame layout that should be replaced
 * @param fragment is the 'new' fragment for replacement
 * @note here the fragment gets not added to the BackStack
 */
fun replaceFragment(fm: FragmentManager, containerViewId: Int, fragment: Fragment) {
    val transaction = fm.beginTransaction()
    transaction.replace(containerViewId, fragment)
    transaction.commit()
    fm.executePendingTransactions()
}

/**
 * Replaces container with a given fragment and remembers the fragment via a tag
 *
 * @param fm FragmentManager of the current activity or fragment
 * @param containerViewId is the id of the frame layout that should be replaced
 * @param fragment is the 'new' fragment for replacement
 * @param tag should be unique to the fragment
 * @note here the fragment gets added to the BackStack
 */
fun replaceFragment(fm: FragmentManager, containerViewId: Int, fragment: Fragment, tag: String) {
    val transaction = fm.beginTransaction()
    // replace and remember fragment by tag
    transaction.replace(containerViewId, fragment, tag)
    transaction.addToBackStack(tag)
    transaction.commit()
    fm.executePendingTransactions()
}