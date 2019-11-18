package com.example.redfruit.ui.toolbar

import android.view.View
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
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            imageView.load(url) {
                crossfade(true)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("iconImage")
    fun loadIconImage(imageView: ImageView, subredditAbout: SubredditAbout?) {

        if (subredditAbout == null) return

        // Not sure why there are two icons available
        val url = if (!subredditAbout.icon_img.isNullOrBlank()) {
            subredditAbout.icon_img
        } else {
            subredditAbout.community_icon
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
