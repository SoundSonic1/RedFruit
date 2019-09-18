package com.example.redfruit

import com.example.redfruit.util.Constants
import com.example.redfruit.util.isValidSub
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IsValidSubTest {
    @Test
    fun validSubTest() {
        runBlocking {
            assertTrue("Default sub must be valid", isValidSub(Constants.DEFAULT_SUB))
            assertTrue("Underscore is valid", isValidSub("memes_of_dank"))
        }
    }

    @Test
    fun invalidSubTest() {
        runBlocking {
            assertFalse(isValidSub("androidde"))
        }
        runBlocking {
            assertFalse("There is no empty sub", isValidSub(""))
        }
        runBlocking {
            assertFalse("Spaces are not allowed", isValidSub("dank memes"))
        }
    }
}
