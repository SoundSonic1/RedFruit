package com.example.redfruit.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.redfruit.data.model.Post


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
