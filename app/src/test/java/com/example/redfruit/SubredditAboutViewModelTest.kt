package com.example.redfruit

import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.ui.extension.getOrAwaitValue
import com.example.redfruit.ui.shared.SubredditAboutViewModel
import com.example.redfruit.util.Constants
import com.example.redfruit.util.InstantExecutorExtension
import com.example.redfruit.util.provideRetroFitRedditApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class SubredditAboutViewModelTest {

    init {
        // override main dispatcher for test
        Dispatchers.setMain(newSingleThreadContext("UI thread"))
    }


    private val repo = SubredditAboutRepository(provideRetroFitRedditApi())

    private val vm = SubredditAboutViewModel(
        Constants.DEFAULT_SUB,
        SortBy.hot,
        repo
    )

    @Test
    fun `set sorting`() {

        vm.setSort(SortBy.controversial)

        assertEquals(SortBy.controversial, vm.sortBy.getOrAwaitValue())
    }

    @Test
    fun `set new subreddit`() {
        runBlocking {
            vm.setSub("News")
        }

        assertEquals("news", vm.data.getOrAwaitValue().display_name, "Should change subreddit to news")
    }

    @Test
    fun `find subreddits with good query`() {

        vm.findSubreddits("android")

        assertEquals(6, vm.subreddits.getOrAwaitValue().size)
    }
}