package com.example.redfruit.ui.toolbar

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.redfruit.R
import com.example.redfruit.data.model.SubredditAbout

object ToolbarBindingAdapters {

    @JvmStatic
    @BindingAdapter("toolbarImage")
    fun loadToolbarImage(imageView: ImageView, url: String?) {
        if (url.isNullOrBlank()) {
            imageView.load("https://wallpaperplay.com/walls/full/2/9/e/31137.jpg") {
                crossfade(true)
            }
        } else {
            imageView.load(url) {
                crossfade(true)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("iconImage")
    fun loadIconImage(imageView: ImageView, subredditAbout: SubredditAbout?) {
        subredditAbout?.let {
            // Not sure why there are two icons available
            val url = if (!it.icon_img.isNullOrBlank()) {
                it.icon_img
            } else {
                it.community_icon
            }
            if (url.isNullOrBlank()) {
                imageView.load(R.drawable.ic_reddit_24dp) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            } else {
                imageView.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.ic_reddit_24dp)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}
