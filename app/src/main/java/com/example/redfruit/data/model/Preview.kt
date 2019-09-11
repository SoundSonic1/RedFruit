package com.example.redfruit.data.model

import com.example.redfruit.data.model.images.RedditImage

/**
 * @property enabled whether the preview is enabled by default
 * @property images Preview can contain images
 */
data class Preview(val enabled: Boolean, val images: List<RedditImage>) {

    val firstImageSource get() = images.firstOrNull()?.source
}
