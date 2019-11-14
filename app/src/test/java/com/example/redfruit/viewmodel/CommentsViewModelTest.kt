package com.example.redfruit.viewmodel

import com.example.redfruit.data.repositories.CommentsRepository
import com.example.redfruit.ui.comments.viewmodel.CommentsViewModel
import com.example.redfruit.ui.extension.getOrAwaitValue
import com.example.redfruit.util.InstantExecutorExtension
import com.example.redfruit.util.provideRedditApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class CommentsViewModelTest {

    init {
        // override main dispatcher for test
        Dispatchers.setMain(newSingleThreadContext("UI thread"))
    }

    private val repo = CommentsRepository(provideRedditApi(), "Android", "dp8qzg")
    private val vm = CommentsViewModel(repo)

    @Test
    fun `load comments on creation`() {
        assertTrue(vm.data.getOrAwaitValue(5L).isNotEmpty())
    }
}