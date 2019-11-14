package com.example.redfruit.repositories

import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortPostBy
import com.example.redfruit.data.repositories.SubredditPostsRepository
import com.example.redfruit.util.Constants
import com.example.redfruit.util.provideRedditApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SubredditPostsRepositoryTest {

    private val repo = SubredditPostsRepository(provideRedditApi())

    @Test
    fun `test post count`() {

        val sortByNew = SortPostBy.new
        runBlocking {
            assertEquals(10, repo.getPosts(Constants.DEFAULT_SUB, sortByNew, 10).size)
            assertEquals(
                20,
                repo.getPosts(Constants.DEFAULT_SUB, SortPostBy.new, 10).size,
                "Should add new posts on top of old ones"
            )
            assertTrue(repo.getPosts("empty", sortByNew, 10).isEmpty())
            assertEquals(10, repo.getPosts(Constants.DEFAULT_SUB, SortPostBy.rising, 10).size)
        }

    }

    @Test
    fun `invalid input`() {
        runBlocking {
            assertEquals(listOf<Post>(), repo.getPosts("androidd", SortPostBy.new, 10))
            assertEquals(listOf<Post>(), repo.getPosts("lounge", SortPostBy.new, 10))
        }
    }
}