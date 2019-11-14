package com.example.redfruit.ui.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemTextBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import kotlinx.android.synthetic.main.post_item_text.view.postTitleAuthorText

class PostTextOnlyViewHolder(
    parent: ViewGroup,
    private val binding: PostItemTextBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.post_item_text,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post, listener: (Post) -> Unit) = with(itemView) {
        // set the text in TextView, fetch image for ImageView and add a listener
        binding.item = item
        itemView.postTitleAuthorText.setOnClickListener {
            listener(item)
        }
    }
}
