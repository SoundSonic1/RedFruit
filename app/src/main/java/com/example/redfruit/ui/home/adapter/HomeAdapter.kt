package com.example.redfruit.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostViewBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.base.GenericAdapter

/**
 * Adapter for the RecyclerView of the HomeFragment
 */
class HomeAdapter(items: MutableList<Post>,
                  listener: (Post) -> Unit) : GenericAdapter<Post>(items, listener) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent)

    override fun getLayoutId(position: Int, obj: Post) = R.layout.post_view

}

/**
 * Manages the individual items with data binding
 */
class PostViewHolder(
    parent: ViewGroup,
    private val binding: PostViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.post_view, parent, false)) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post, listener: (Post) -> Unit) = with(itemView) {
        // set the text in TextView, fetch image for ImageView and add a listener
        // TODO: use data binding for ImageView
        binding.item = item
        itemView.setOnClickListener {
            listener(item)
        }
    }
}