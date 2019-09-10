package com.example.redfruit.util

import java.net.URL

/**
 * @param url to fetch response
 * @return the response as a string
 */
fun getResponse(url: String): String = try {
    URL(url).readText()
} catch (e: Exception) {
    // invalid url
    ""
}