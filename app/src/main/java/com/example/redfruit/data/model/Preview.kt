package com.example.redfruit.data.model

import com.example.redfruit.data.model.images.RedditImage

/**
 * Post preview
 *
 * @property enabled whether the preview is enabled by default
 * @property images Preview can contain images
 */
data class Preview(
    val enabled: Boolean = false,
    val images: List<RedditImage> = listOf()
) {

    val firstImageSource get() = images.firstOrNull()?.source
}
