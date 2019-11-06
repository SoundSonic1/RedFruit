package com.example.redfruit.data.model

import com.example.redfruit.data.model.images.RedditImage
import com.squareup.moshi.JsonClass

/**
 * Post preview
 *
 * @property enabled whether the preview is enabled by default
 * @property images Preview can contain images
 */
@JsonClass(generateAdapter = true)
data class Preview(
    val enabled: Boolean = false,
    val images: List<RedditImage> = listOf()
) {

    val firstImage get() = images.firstOrNull()
}
