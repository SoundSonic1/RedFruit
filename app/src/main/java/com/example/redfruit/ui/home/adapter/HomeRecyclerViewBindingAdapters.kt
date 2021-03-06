package com.example.redfruit.ui.home.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.api.clear
import coil.api.load
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.util.SizableColorDrawable

object HomeRecyclerViewBindingAdapters {
    /**
     * items might be null because the ViewModel which provides them is
     * not yet initialized
     */
    @JvmStatic
    @BindingAdapter("items")
    fun setRecyclerViewProperties(recyclerViewHome: RecyclerView, items: List<Post>?) {
        val adapter = recyclerViewHome.adapter
        if (items != null && adapter is PostListAdapter) {
            adapter.submitList(items)
        }
    }

    @JvmStatic
    @BindingAdapter("imagePreview")
    fun loadImagePreview(imageView: ImageView, preview: Preview?) {

        // get the last resolution image instead of source to save bandwidth
        // in RecyclerView
        val image = preview?.firstImage?.resolutions?.last()

        if (image == null) {
            imageView.clear()
        } else {
            imageView.load(image.url) {
                crossfade(true)
                placeholder(
                    SizableColorDrawable(
                    0x222222,
                        image.width,
                        image.height
                    )
                )
            }
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImageToView(imageView: ImageView, url: String?) {
        if (!url.isNullOrBlank()) {
            imageView.load(url) {
                crossfade(true)
            }
        } else {
            imageView.load(R.drawable.ic_link) {
                crossfade(true)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("imageDrawable")
    fun loadDrawableIntoImageView(imageView: ImageView, url: String) {
        // TODO: use ViewTarget
        Coil.load(imageView.context, url) {
            target {
                imageView.setImageDrawable(it)
            }
        }
    }
}
