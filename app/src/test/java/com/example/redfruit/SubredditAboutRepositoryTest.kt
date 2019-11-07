package com.example.redfruit

import com.example.redfruit.data.api.IRedditApi
import com.example.redfruit.data.api.RedditApi
import com.example.redfruit.data.api.TokenAuthenticator
import com.example.redfruit.data.api.TokenProvider
import com.example.redfruit.data.repositories.SubredditAboutRepository
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
            val subredditAbout = repo.getData("android")

            assertEquals("Android", subredditAbout?.display_name)
            assertEquals(false, subredditAbout?.over_18)
        }

        runBlocking {
            val subredditAbout = repo.getData("memes_of_dank")
            assertNotNull(subredditAbout, "Underscore is valid")
            assertEquals("Memes_of_dank", subredditAbout?.display_name)
        }

    }

    @Test
    fun `invalid input test`() {
        runBlocking {
            assertEquals( null, repo.getData("androidd"), "Subreddit androidd does not exist.")
        }
        runBlocking {
            assertEquals(null, repo.getData(""), "Empty sub name is not allowed.")
        }

        runBlocking {
            assertEquals( null, repo.getData("dank memes"), "Spaces are not allowed")
        }
    }

    @Test
    fun `test find subreddits count`() {
        runBlocking {
            assertEquals(9, repo.findSubreddits("android", 9).size)
            assertEquals(12, repo.findSubreddits("android", 12).size)
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