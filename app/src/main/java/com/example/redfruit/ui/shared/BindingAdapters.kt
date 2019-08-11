package com.example.redfruit.ui.shared

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.home.adapter.HomeAdapter

/**
 * Collect all BindingAdapters here
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("items")
    // items might be null because the ViewModel which provides them is
    // not yet initialized
    fun setRecyclerViewProperties(recyclerViewHome: RecyclerView, items: Collection<Post>?) {
        if (items != null && recyclerViewHome.adapter is HomeAdapter) {
            (recyclerViewHome.adapter as HomeAdapter).refreshItems(items)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String) {
        if (url.isNotBlank()) {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }
}