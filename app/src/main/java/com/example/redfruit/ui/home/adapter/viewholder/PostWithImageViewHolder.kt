package com.example.redfruit.ui.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemImageBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import kotlinx.android.synthetic.main.post_item_image.view.*

class PostWithImageViewHolder(
    parent: ViewGroup,
    private val binding: PostItemImageBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.post_item_image,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post, listener: (Post) -> Unit) {
        binding.item = item
        itemView.postTitleAuthorIncludeImage.setOnClickListener {
            listener(item)
        }
    }
}
