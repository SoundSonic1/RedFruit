package com.example.redfruit.data.model.images

import com.example.redfruit.data.model.interfaces.ISource
import com.squareup.moshi.JsonClass

/**
 * Required information for image fetching library
 *
 * @property url of the image
 * @property width of the image
 * @property height of the image
 */
@JsonClass(generateAdapter = true)
data class ImageSource(
    override val url: String,
    val width: Int,
    val height: Int
) : ISource