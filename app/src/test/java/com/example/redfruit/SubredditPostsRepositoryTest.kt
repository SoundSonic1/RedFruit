package com.example.redfruit

import com.beust.klaxon.Parser
import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortBy
import com.example.redfruit.data.repositories.SubredditPostsRepository
import com.example.redfruit.util.Constants
import com.example.redfruit.util.KlaxonFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class SubredditPostsRepositoryTest {

    private val klaxonFactory = KlaxonFactory()

    private val authenticator = TokenAuthenticator(TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString(), klaxonFactory))

    private val redditApi: IRedditApi =
        RedditApi(
            authenticator,
            OkHttpClient.Builder().authenticator(authenticator).build(),
            KlaxonFactory(),
            Parser.default()
        )

    private lateinit var repo: SubredditPostsRepository


    @Before
    fun setUp() {
        repo = SubredditPostsRepository(redditApi)
    }

    @Test
    fun getDataCountTest() {

        val sortByNew = SortBy.new
        runBlocking {
            assertEquals(10, repo.getPosts(Constants.DEFAULT_SUB, sortByNew, 10).size)
            assertEquals(
                "Should add new posts on top of old ones",
                20,
                repo.getPosts(Constants.DEFAULT_SUB, SortBy.new, 10).size
            )
            assertEquals(0, repo.getPosts("empty", sortByNew, 10).size)
            assertEquals(10, repo.getPosts(Constants.DEFAULT_SUB, SortBy.rising, 10).size)
        }

    }

    @Test
    fun getInvalidDataTest() {
        runBlocking {
            assertEquals(listOf<Post>(), repo.getPosts("androidd", SortBy.new, 10))
            assertEquals(listOf<Post>(), repo.getPosts("lounge", SortBy.new, 10))
        }
    }
}