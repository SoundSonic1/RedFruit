package com.example.redfruit.ui.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemSecureMediaBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.shared.OnPostClickHandler

class PostWithSecureMediaViewHolder(
    parent: ViewGroup,
    private val onPostClickHandler: OnPostClickHandler,
    private val binding: PostItemSecureMediaBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.post_item_secure_media,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post) {
        binding.item = item
        binding.clickHandler = onPostClickHandler
    }
}
