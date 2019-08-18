package com.example.redfruit.ui.shared

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
// import com.example.redfruit.R
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
    // url can be null
    fun loadImage(imageView: ImageView, url: String?) {
        if (!url.isNullOrBlank()) {
            Glide.with(imageView.context)
                .load(url)
                .fitCenter()
                //.placeholder(R.drawable.ic_refresh_white_24dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        } else {
            // make sure empty ImageView stays empty
            Glide.with(imageView.context).clear(imageView)
        }
    }
}