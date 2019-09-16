package com.example.redfruit.data.model.interfaces

/**
 * Required specs taken from https://oembed.com/
 */
interface IoEmbed {
    val type: String
    val version: String
    val html: String
    val width: Int
    val height: Int
}