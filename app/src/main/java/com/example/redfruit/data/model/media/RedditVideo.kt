package com.example.redfruit.data.model.media

import com.beust.klaxon.Json
import com.example.redfruit.data.model.interfaces.ISource

/**
 * Streamable video via dash url
 */
data class RedditVideo(
    @Json(name = "dash_url")
    override val url: String = "",
    val height: Int,
    val width: Int
) : ISource
