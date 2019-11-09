package com.example.redfruit.data.model.interfaces

/**
 * Required specs taken from https://youtubeoEmbed.com/
 */
interface IoEmbed {
    val provider_url: String
    val type: String
    val html: String
    val width: Int
    val height: Int
}
