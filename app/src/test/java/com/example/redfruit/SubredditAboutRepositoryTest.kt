package com.example.redfruit

import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.data.repositories.SubredditAboutRepository
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class SubredditAboutRepositoryTest {

    private val authenticator =
        TokenAuthenticator(TokenProvider(BuildConfig.ClientId, UUID.randomUUID().toString()))

    private val redditApi: IRedditApi =
        RedditApi(
            authenticator,
            OkHttpClient.Builder().authenticator(authenticator).build()
        )

    private val repo: SubredditAboutRepository = SubredditAboutRepository(redditApi, mutableMapOf())


    @Test
    fun `valid input`() {

        runBlocking {
            val subredditAbout = repo.getData("memes_of_danK")
            assertNotNull(subredditAbout, "Underscore is valid")
            assertEquals("Memes_of_dank", subredditAbout?.display_name)
        }

    }

    @Test
    fun `nsfw subreddit`() {
        runBlocking {
            val subredditAbout = repo.getData("gonewild")!!

            assertEquals("gonewild", subredditAbout.display_name)
            assertTrue(subredditAbout.over18)
        }
    }

    @Test
    fun `invalid input`() {
        runBlocking {
            assertNull(repo.getData("androidd"), "Subreddit androidd does not exist.")
            assertNull(repo.getData(""), "Empty sub name is not allowed.")
            assertNull(repo.getData("dank memes"), "Spaces are not allowed")
        }
    }

    @Test
    fun `check subreddits count`() {
        runBlocking {
            assertEquals(9, repo.findSubreddits("android", 9).size)
            assertEquals(1, repo.findSubreddits("android", 1).size)
            assertEquals(0, repo.findSubreddits("android", -1).size)
            assertEquals(0, repo.findSubreddits("android", 0).size)
        }
    }

    @Test
    fun `find private sub`() {
        runBlocking {
            val sub = repo.findSubreddits("fgousa", 1)
            assertEquals("FGOUSA", sub[0].display_name, "Private subreddits are searchable")
        }
    }
}