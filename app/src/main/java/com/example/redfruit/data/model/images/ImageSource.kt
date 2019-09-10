package com.example.redfruit.data.model.images

import com.example.redfruit.data.model.interfaces.ISource

/** Contains all the information to fetch the image
 *
 * @property url of the image
 * @property width of the image
 * @property height of the image
 */
data class ImageSource(override val url: String,
                       val width: Int,
                       val height: Int
) : ISource