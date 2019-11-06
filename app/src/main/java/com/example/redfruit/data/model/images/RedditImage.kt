package com.example.redfruit.data.model.images

import com.squareup.moshi.JsonClass

/**
 * Reddit gives source image and other image variations
 *
 * @property source original image
 * @property resolutions various (compressed) versions of the image
 */
@JsonClass(generateAdapter = true)
data class RedditImage(val source: ImageSource, val resolutions: List<ImageSource>)