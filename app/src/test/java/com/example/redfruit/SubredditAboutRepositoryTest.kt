package com.example.redfruit

import com.example.redfruit.data.api.SubredditAboutRepository
import com.example.redfruit.data.model.SubredditAbout
import org.junit.Assert.assertEquals
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
        val subredditAbout = repo.getData("android")

        assertEquals("Android", subredditAbout.display_name)
        assertEquals(false, subredditAbout.over_18)

    }

    @Test
    fun getInvalidDataTest() {
        val subredditAbout = repo.getData("androidd")

        assertEquals(SubredditAbout("androidd"), subredditAbout)
    }
}