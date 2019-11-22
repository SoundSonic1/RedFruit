package com.example.redfruit.ui.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemLinkBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.shared.OnPostClickHandler

class PostWithLinkViewHolder(
    parent: ViewGroup,
    private val clickHandler: OnPostClickHandler,
    private val binding: PostItemLinkBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.post_item_link,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post) = with(itemView) {
        binding.item = item
        binding.clickHandler = clickHandler
    }
}
