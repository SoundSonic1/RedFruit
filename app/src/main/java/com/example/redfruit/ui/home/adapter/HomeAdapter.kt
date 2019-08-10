package com.example.redfruit.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostViewBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.base.GenericAdapter

class HomeAdapter(items: MutableList<Post>,
                  context: Context,
                  listener: (Post) -> Unit) : GenericAdapter<Post>(items, context, listener) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent)

    override fun getLayoutId(position: Int, obj: Post) = R.layout.post_view

}

/**
 * Manages the individual items with bind()
 */
class PostViewHolder(
    parent: ViewGroup,
    private val binding: PostViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.post_view, parent, false)) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post, listener: (Post) -> Unit) = with(itemView) {
        // set the text in TextView and add a listener
        binding.item = item
        itemView.setOnClickListener {
            listener(item)
        }
    }
}