package com.example.redfruit.data.model.media

import com.squareup.moshi.JsonClass

/**
 * Only HTTPS allowed
 */
@JsonClass(generateAdapter = true)
data class SecureMedia(
    val redditVideo: RedditVideo?,
    val youtubeoEmbed: YoutubeoEmbed?
)
