package com.example.redfruit

import com.beust.klaxon.Parser
import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.util.KlaxonFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.*

class SubredditAboutRepositoryTest {

    private lateinit var repo: SubredditAboutRepository

    private val authenticator = TokenAuthenticator(TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString()))

    private val redditApi: IRedditApi =
        RedditApi(
            authenticator,
            OkHttpClient.Builder().authenticator(authenticator).build(),
            KlaxonFactory(),
            Parser.default()
        )


    @Before
    fun setUp() {
        repo = SubredditAboutRepository(
            redditApi,
            mutableMapOf()
        )
    }

    @Test
    fun getValidDataTest() {
        runBlocking {
            val subredditAbout = repo.getData("android")

            assertEquals("Android", subredditAbout?.display_name)
            assertEquals(false, subredditAbout?.over18)
        }

        runBlocking {
            val subredditAbout = repo.getData("memes_of_dank")
            assertNotNull("Underscore is valid", subredditAbout)
            assertEquals("Memes_of_dank", subredditAbout?.display_name)
        }

    }

    @Test
    fun getInvalidDataTest() {
        runBlocking {
            assertEquals("Subreddit androidd does not exist.", null, repo.getData("androidd"))
        }
        runBlocking {
            assertEquals("Empty sub name is not allowed.",null, repo.getData(""))
        }

        runBlocking {
            assertEquals("Spaces are not allowed", null, repo.getData("dank memes"))
        }
    }

    @Test
    fun testFindSubreddits() {
        runBlocking {

            assertEquals(9, repo.findSubreddits("android", 9).size)
            assertEquals(12, repo.findSubreddits("android", 12).size)
            assertEquals("Private subreddit is searchable",1, repo.findSubreddits("fgousa", 1).size)
        }
    }
}