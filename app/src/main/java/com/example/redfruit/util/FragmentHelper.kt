package com.example.redfruit.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * @author https://thuytrinh.github.io/android/kotlin/2017/02/07/find-fragments.html
 * @param tag is a string used to identify the fragment
 * @return Fragment: returns either a fragment found in the FragmentManager
 * or from a higher order function
 */
fun AppCompatActivity.findFragmentByTag(tag: String, ifNone: (String) -> Fragment): Fragment {
    return supportFragmentManager.findFragmentByTag(tag) ?: ifNone(tag)
}

/**
 * Replaces container with a given fragment
 *
 * @param containerViewId is the id of the frame layout that should be replaced
 * @param fragment is the 'new' fragment for replacement
 * @note here the fragment gets not added to the BackStack
 */
fun AppCompatActivity.replaceFragment(containerViewId: Int, fragment: Fragment) {
    val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    transaction.replace(containerViewId, fragment)
    transaction.commit()
    manager.executePendingTransactions()
}

/**
 * Replaces container with a given fragment and remembers the fragment via a tag
 *
 * @param containerViewId is the id of the frame layout that should be replaced
 * @param fragment is the 'new' fragment for replacement
 * @param tag should be unique to the fragment
 * @note here the fragment gets added to the BackStack
 */
fun AppCompatActivity.replaceFragment(containerViewId: Int, fragment: Fragment, tag: String) {
    val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    // replace and remember fragment by tag
    transaction.replace(containerViewId, fragment, tag)
    transaction.addToBackStack(tag)
    transaction.commit()
    manager.executePendingTransactions()
}