package com.example.redfruit.ui.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemYoutubeBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import kotlinx.android.synthetic.main.post_item_youtube.view.*

class PostWithYoutubeViewHolder(
    parent: ViewGroup,
    lifeCycle: Lifecycle,
    private val binding: PostItemYoutubeBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.post_item_youtube,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {
    init {
        lifeCycle.addObserver(itemView.postYoutubePlayerView)
    }
    override fun bind(item: Post, listener: (Post) -> Unit) {
        binding.item = item
        itemView.postTitleAuthorIncludeYoutube.setOnClickListener {
            listener(item)
        }
    }
}
