package com.example.redfruit

import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.repositories.SubredditPostsRepository
import com.example.redfruit.util.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class SubredditPostsRepositoryTest {

    private val authenticator =
        TokenAuthenticator(TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString()))

    private val redditApi: IRedditApi =
        RedditApi(
            authenticator,
            OkHttpClient.Builder().authenticator(authenticator).build()
        )

    private val repo = SubredditPostsRepository(redditApi)

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