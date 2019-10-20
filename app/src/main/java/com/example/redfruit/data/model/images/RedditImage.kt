package com.example.redfruit.data.model.images

/**
 * Reddit gives source image and other image variations
 *
 * @property source original image
 * @property resolutions various (compressed) versions of the image
 */
data class RedditImage(val source: ImageSource, val resolutions: List<ImageSource>)