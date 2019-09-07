package com.example.redfruit.util

import android.graphics.drawable.ColorDrawable

/**
 * Used to get a placeholder drawable with the exact same size as the image
 * @param color of the drawable
 * @param width of the drawable
 * @param height of the drawable
 * @author https://stackoverflow.com/questions/2548466/how-to-use-colordrawable-with-imageview
 */
class SizableColorDrawable(color: Int,
                           private val width: Int,
                           private val height: Int
) : ColorDrawable(color) {

    override fun getIntrinsicWidth(): Int = width

    override fun getIntrinsicHeight(): Int = height
}