package com.example.redfruit.ui.home.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.util.SizableColorDrawable
import com.ortiz.touchview.TouchImageView

/**
 * Collect all HomeRecyclerViewBindingAdapters here
 */
object HomeRecyclerViewBindingAdapters {
    /**
     * items might be null because the ViewModel which provides them is
     * not yet initialized
     */
    @JvmStatic
    @BindingAdapter("items")
    fun setRecyclerViewProperties(recyclerViewHome: RecyclerView, items: List<Post>?) {
        if (items != null && recyclerViewHome.adapter is HomeAdapter) {
            (recyclerViewHome.adapter as HomeAdapter).notifyChanges(items)
        }
    }

    @JvmStatic
    @BindingAdapter("imagePreview")
    fun loadImagePreview(imageView: ImageView, preview: Preview?) {
        preview?.firstImageSource?.let {
            imageView.load(it.url) {
                crossfade(true)
                placeholder(SizableColorDrawable(0x222222, it.width, it.height))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("youtubeThumbnail")
    fun loadYoutubeThumbnail(imageView: ImageView, url: String) {
       if (url.isNotBlank()) {
           imageView.load(url) {
               crossfade(true)
           }
       }
    }

    @JvmStatic
    @BindingAdapter("imageFragment")
    fun loadImageToFragment(imageView: TouchImageView, url: String) {
        if (url.isNotBlank()) {
            imageView.load(url) {
                crossfade(true)
            }
        }
    }
}