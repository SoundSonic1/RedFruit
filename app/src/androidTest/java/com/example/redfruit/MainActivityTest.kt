package com.example.redfruit

import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.redfruit.ui.MainActivity
import com.example.redfruit.util.Constants
import com.example.redfruit.util.clickOnViewChild
import com.example.redfruit.util.scrollToBottom
import com.example.redfruit.util.submitText
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun appBarLayout() {
        launchActivity<MainActivity>()
        onView(withId(R.id.appBarLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnPostTitle() {
        launchActivity<MainActivity>()

        // TODO: use idling resources with coroutines
        Thread.sleep(3500)

        onView(withId(R.id.recyclerViewPosts))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.postTitle)
                )
            )

        Thread.sleep(2000)

        onView(withId(R.id.postDetailCardView)).check(matches(isDisplayed()))
    }

    @Test
    fun scrollToBottomAndLoadMore() {

        val scrollPos = 20

        launchActivity<MainActivity>()

        Thread.sleep(3500)

        onView(withId(R.id.recyclerViewPosts)).perform(scrollToBottom())

        Thread.sleep(2000)

        onView(withId(R.id.recyclerViewPosts))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(scrollPos)
            )

        onView(withId(R.id.recyclerViewPosts))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    scrollPos,
                    clickOnViewChild(R.id.postTitle)
                )
            )

        Thread.sleep(2000)

        onView(withId(R.id.postDetailCardView)).check(matches(isDisplayed()))
    }

    @Test
    fun performSubredditSearch() {

        val testSub = "dankmemes"

        launchActivity<MainActivity>()

        // Navigate to default sub
        onView(withId(R.id.action_search_subreddit)).perform(click())
        onView(isAssignableFrom(SearchView::class.java)).perform(submitText(Constants.DEFAULT_SUB))

        Thread.sleep(3000)

        // Navigate to test sub
        onView(withId(R.id.action_search_subreddit)).perform(click())
        onView(isAssignableFrom(SearchView::class.java)).perform(submitText(testSub))

        Thread.sleep(3000)

        onView(withText(testSub)).check(matches(withParent(withId(R.id.toolbar))))
    }
}
