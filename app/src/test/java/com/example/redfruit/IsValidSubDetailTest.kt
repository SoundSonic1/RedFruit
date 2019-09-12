package com.example.redfruit

import com.example.redfruit.util.Constants
import com.example.redfruit.util.isValidSubDetail
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IsValidSubDetailTest {
    @Test
    fun validSub() {
        runBlocking {
            assertTrue("Default sub must be valid", isValidSubDetail(Constants.DEFAULT_SUB))
            assertTrue("Underscore is valid", isValidSubDetail("memes_of_dank"))
        }
    }

    @Test
    fun invalidSub() {
        runBlocking {
            assertFalse(isValidSubDetail("androidde"))
        }
        runBlocking {
            assertFalse("There is no empty sub", isValidSubDetail(""))
        }
        runBlocking {
            assertFalse("Spaces are not allowed", isValidSubDetail("dank memes"))
        }
    }
}
