package com.example.redfruit

import com.example.redfruit.data.api.SubredditAboutRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SubredditAboutRepositoryTest {

    private lateinit var repo: SubredditAboutRepository

    @Before
    fun setUp() {
        repo = SubredditAboutRepository(mutableMapOf())
    }

    @Test
    fun getValidDataTest() {
        runBlocking {
            val subredditAbout = repo.getData("android")

            assertEquals("Android", subredditAbout?.display_name)
            assertEquals(false, subredditAbout?.over_18)
        }

        runBlocking {
            val subredditAbout = repo.getData("memes_of_dank")
            assertTrue("Underscore is valid", subredditAbout != null)
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
}