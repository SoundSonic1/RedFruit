package com.example.redfruit.ui.home.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.api.load
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.example.redfruit.util.SizableColorDrawable
import com.nguyencse.URLEmbeddedData
import com.nguyencse.URLEmbeddedView

/**
 * Collect all RecyclerViewBindingAdapters here
 */
object RecyclerViewBindingAdapters {
    /**
     * items might be null because the ViewModel which provides them is
     * not yet initialized
     */
    @JvmStatic
    @BindingAdapter("items")
    fun setRecyclerViewProperties(recyclerViewHome: RecyclerView, items: List<Post>?) {
        if (items != null && recyclerViewHome.adapter is PostListAdapter) {
            (recyclerViewHome.adapter as PostListAdapter).submitList(items)
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

    @JvmStatic
    @BindingAdapter("urlEmbedded")
    fun loadUrlPreview(urlEmbeddedView: URLEmbeddedView, post: Post) {
        if (post.urlEmbeddedData == null) {
            urlEmbeddedView.setURL(post.url, object : URLEmbeddedView.OnLoadURLListener {
                override fun onLoadURLCompleted(data: URLEmbeddedData) {
                    post.urlEmbeddedData = data
                    urlEmbeddedView.setData(data)
                }
            })
        } else {
            urlEmbeddedView.setData(post.urlEmbeddedData!!)
        }
    }
}