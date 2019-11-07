package com.example.redfruit

import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.repositories.SubredditPostsRepository
import com.example.redfruit.util.Constants
import com.example.redfruit.util.provideRetroFitRedditApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SubredditPostsRepositoryTest {

    private val repo = SubredditPostsRepository(provideRetroFitRedditApi())

    @Test
    fun `test post count`() {

        val sortByNew = SortBy.new
        runBlocking {
            assertEquals(10, repo.getPosts(Constants.DEFAULT_SUB, sortByNew, 10).size)
            assertEquals(
                20,
                repo.getPosts(Constants.DEFAULT_SUB, SortBy.new, 10).size,
                "Should add new posts on top of old ones"
            )
            assertTrue(repo.getPosts("empty", sortByNew, 10).isEmpty())
            assertEquals(10, repo.getPosts(Constants.DEFAULT_SUB, SortBy.rising, 10).size)
        }

    }

    @Test
    fun `invalid input`() {
        runBlocking {
            assertEquals(listOf<Post>(), repo.getPosts("androidd", SortBy.new, 10))
            assertEquals(listOf<Post>(), repo.getPosts("lounge", SortBy.new, 10))
        }
    }
}