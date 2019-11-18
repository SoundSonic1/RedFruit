package com.example.redfruit.ui.comments.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.redfruit.data.model.Preview
import com.example.redfruit.ui.comments.groupie.CommentExpandableGroup
import com.example.redfruit.util.SizableColorDrawable
import com.xwray.groupie.GroupAdapter

object CommentsBindingAdapters {

    @JvmStatic
    @BindingAdapter("comments")
    fun loadComments(recyclerView: RecyclerView, comments: List<CommentExpandableGroup>?) {
        val adapter = recyclerView.adapter
        if (comments != null && adapter is GroupAdapter) {
            adapter.clear()
            adapter.addAll(comments)
        }
    }

    @JvmStatic
    @BindingAdapter("loadPreviewDetail")
    fun loadPostPreview(imageView: ImageView, preview: Preview?) {

        val image = preview?.firstImage?.source

        if (image == null || !preview.enabled) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
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
}
