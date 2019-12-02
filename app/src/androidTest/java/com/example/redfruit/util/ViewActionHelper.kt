package com.example.redfruit.util

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

/**
 * @author https://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item
 */
fun clickOnViewChild(viewId: Int) = object : ViewAction {

    override fun getConstraints() = null

    override fun getDescription() = "Click on a child view with specified id."

    override fun perform(uiController: UiController, view: View) =
        ViewActions.click().perform(uiController, view.findViewById<View>(viewId))
}

fun scrollToBottom() = object : ViewAction {
    override fun getDescription(): String {
        return "scroll RecyclerView to bottom"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf<View>(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount
        val position = itemCount?.minus(1) ?: 0
        recyclerView.scrollToPosition(position)
        uiController?.loopMainThreadUntilIdle()
    }
}

fun submitText(text: String) = object : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
    }

    override fun getDescription(): String {
        return "Set text and submit"
    }

    override fun perform(uiController: UiController, view: View) {
        // submit=true will fire search
        (view as SearchView).setQuery(text, true)
    }
}
