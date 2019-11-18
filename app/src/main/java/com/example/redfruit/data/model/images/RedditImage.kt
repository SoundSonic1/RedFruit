package com.example.redfruit.data.model.images

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Reddit gives source image and other image variations
 *
 * @property source original image
 * @property resolutions various (compressed) versions of the image
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class RedditImage(val source: ImageSource, val resolutions: List<ImageSource>) : Parcelable
