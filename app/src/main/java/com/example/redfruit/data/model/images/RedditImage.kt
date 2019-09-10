package com.example.redfruit.data.model.images

/**
 * @property source original image
 * @property resolutions various (compressed) versions of the image
 */
data class RedditImage(val source: ImageSource, val resolutions: Collection<ImageSource>)