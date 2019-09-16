package com.example.redfruit.data.model.media

import com.example.redfruit.data.model.interfaces.IoEmbed

/**
 * Only HTTPS allowed
 */
data class SecureMedia(
    val redditVideo: RedditVideo?,
    val oembed: IoEmbed?
)
