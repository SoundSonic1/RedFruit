package com.example.redfruit.viewmodel

import com.example.redfruit.data.model.enumeration.SortPostBy
import com.example.redfruit.data.repositories.SubredditPostsRepository
import com.example.redfruit.ui.home.viewmodel.HomePostsViewModel
import com.example.redfruit.util.Constants
import com.example.redfruit.util.InstantExecutorExtension
import com.example.redfruit.util.getOrAwaitValue
import com.example.redfruit.util.provideRedditApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class HomePostViewModelTest {

    init {
        // override main dispatcher for test
        Dispatchers.setMain(newSingleThreadContext("UI thread"))
    }

    private val repo = SubredditPostsRepository(provideRedditApi())

    private val vm = HomePostsViewModel(Constants.DEFAULT_SUB, SortPostBy.hot, repo)

    @Test
    fun `load posts automatically on creation`() {
        assertTrue(vm.data.getOrAwaitValue(TIME_SECONDS).isNotEmpty())
    }

    @Test
    fun `load more posts`() {
        val oldPosts = vm.data.getOrAwaitValue(TIME_SECONDS)
        vm.loadMoreData(10)
        // TODO: solve this without sleep
        Thread.sleep(1500)

        assertTrue(vm.data.getOrAwaitValue(TIME_SECONDS).size > oldPosts.size)
    }

    @Test
    fun `refresh data`() {
        vm.data.getOrAwaitValue(TIME_SECONDS)
        vm.refreshSub()
        assertTrue(vm.data.getOrAwaitValue(TIME_SECONDS).isNotEmpty())
    }

    @Test
    fun `change subreddit`() {
        vm.changeSub("dankmemes")
        vm.changeSub("news")

        assertEquals("news", vm.subReddit)
    }

    companion object {
        private const val TIME_SECONDS: Long = 30
    }
}