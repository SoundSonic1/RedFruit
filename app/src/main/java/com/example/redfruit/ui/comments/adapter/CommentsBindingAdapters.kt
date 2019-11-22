package com.example.redfruit.ui.comments.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.redfruit.data.model.Post
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
    fun loadPostPreview(imageView: ImageView, post: Post?) {

        val preview = post?.preview

        val image = preview?.firstImage?.source

        if (image == null) {
            imageView.visibility = View.GONE
            return
        }

        when {
            post.secureMedia != null -> imageView.load(image.url) {
                crossfade(true)
                placeholder(
                    SizableColorDrawable(
                        0x222222,
                        image.width,
                        image.height
                    )
                )
            }
            post.preview.enabled -> imageView.load(image.url) {
                crossfade(true)
                placeholder(
                    SizableColorDrawable(
                        0x222222,
                        image.width,
                        image.height
                    )
                )
            }
            else -> imageView.visibility = View.GONE
        }
    }
}
