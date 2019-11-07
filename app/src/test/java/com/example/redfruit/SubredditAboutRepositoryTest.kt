package com.example.redfruit

import com.example.redfruit.data.repositories.SubredditAboutRepository
import com.example.redfruit.util.provideRetroFitRedditApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SubredditAboutRepositoryTest {

    private val repo: SubredditAboutRepository =
        SubredditAboutRepository(provideRetroFitRedditApi())

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
            /*assertTrue(repo.findSubreddits("android", -1).isEmpty())
            assertTrue(repo.findSubreddits("android", 0).isEmpty())*/
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