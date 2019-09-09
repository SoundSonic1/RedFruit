package com.example.redfruit.ui.shared

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.redfruit.data.model.Images.ImageSource
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.home.adapter.HomeAdapter
import com.example.redfruit.util.SizableColorDrawable

/**
 * Collect all BindingAdapters here
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("items")
    // items might be null because the ViewModel which provides them is
    // not yet initialized
    fun setRecyclerViewProperties(recyclerViewHome: RecyclerView, items: List<Post>?) {
        if (items != null && recyclerViewHome.adapter is HomeAdapter) {
            (recyclerViewHome.adapter as HomeAdapter).notifyChanges(items)
        }
    }

    @JvmStatic
    @BindingAdapter("imageSource")
    // url can be null
    fun loadImage(imageView: ImageView, image: ImageSource?) {
        if (image != null) {
            imageView.load(image.url) {
                crossfade(true)
                placeholder(SizableColorDrawable(0xAAAAAA, image.width, image.height))
            }
        } else {
            // make sure empty ImageView stays empty
            imageView.setImageDrawable(null)
        }
    }
}