package com.example.redfruit.ui.comments.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.ui.comments.groupie.CommentExpandableGroup
import com.xwray.groupie.GroupAdapter
import io.noties.markwon.Markwon

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
    @BindingAdapter("markDownText")
    fun loadMarkDownText(textView: TextView, text: String) {
        Markwon.create(textView.context).apply {
            setMarkdown(textView, text)
        }
    }
}
