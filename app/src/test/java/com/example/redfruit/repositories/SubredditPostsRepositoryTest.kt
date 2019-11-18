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
            assertEquals(10, repo.loadMorePosts(Constants.DEFAULT_SUB, sortByNew, 10).size)
            assertEquals(
                20,
                repo.loadMorePosts(Constants.DEFAULT_SUB, SortPostBy.new, 10).size,
                "Should add new posts on top of old ones"
            )
            assertTrue(repo.loadMorePosts("empty", sortByNew, 10).isEmpty())
            assertEquals(10, repo.loadMorePosts(Constants.DEFAULT_SUB, SortPostBy.rising, 10).size)
        }
    }

    @Test
    fun `invalid sub name input`() {
        runBlocking {
            assertTrue(repo.loadMorePosts("androidd", SortPostBy.new, 10).isEmpty())
        }
    }

    @Test
    fun `no access to subreddit`() {
       runBlocking {
           assertTrue(repo.loadMorePosts("lounge", SortPostBy.new, 10).isEmpty())
       }
    }

    @Test
    fun `invalid limit input`() {
        runBlocking {
            assertTrue(repo.loadMorePosts("linux", SortPostBy.new, 0).isEmpty())
            assertTrue(repo.loadMorePosts("linux", SortPostBy.new, -1).isEmpty())
        }
    }
}
