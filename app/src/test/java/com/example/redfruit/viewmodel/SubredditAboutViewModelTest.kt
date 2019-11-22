package com.example.redfruit.viewmodel

import com.example.redfruit.data.model.enumeration.SortPostBy
import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants
import com.example.redfruit.util.InstantExecutorExtension
import com.example.redfruit.util.getOrAwaitValue
import com.example.redfruit.util.provideRedditApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class SubredditAboutViewModelTest {

    init {
        // override main dispatcher for test
        Dispatchers.setMain(newSingleThreadContext("UI thread"))
    }

    private val repo = SubredditAboutRepository(provideRedditApi())

    private val vm = SubredditAboutViewModel(
        Constants.DEFAULT_SUB,
        SortPostBy.hot,
        repo
    )

    @Test
    fun `set sorting`() {

        vm.setSort(SortPostBy.controversial)

        assertEquals(SortPostBy.controversial, vm.sortPostBy.getOrAwaitValue())
    }

    @Test
    fun `set new subreddit`() {
        runBlocking {
            vm.setSub("News")
        }

        assertEquals(
            "news",
            vm.data.getOrAwaitValue().display_name,
            "Should change subreddit to news"
        )
    }

    @Test
    fun `set wrong subreddit`() {
        runBlocking {
            assertEquals(false, vm.setSub("androidd"))
        }
    }

    @Test
    fun `find subreddits with good query`() {

        vm.findSubreddits("android")

        assertEquals(6, vm.subreddits.getOrAwaitValue().size)
    }

    @Test
    fun `change subreddit on click event`() {
        vm.findSubreddits("android")

        assertTrue(
            vm.subreddits.getOrAwaitValue().isNotEmpty(),
            "Search result should not be empty"
        )

        assertTrue(vm.changeSubOnClick(0))
        assertTrue(vm.changeSubOnClick(1))
        assertFalse(vm.changeSubOnClick(-1))
    }
}
