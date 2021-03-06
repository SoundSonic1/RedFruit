package com.example.redfruit.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostTextOnlyViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithImageViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithLinkViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithSecureMediaViewHolder
import com.example.redfruit.ui.shared.OnPostClickHandler

/**
 * Adapter for SubredditPostsFragment
 */
class PostListAdapter(
    private val onPostClickHandler: OnPostClickHandler
) : ListAdapter<Post, AbstractViewHolder<Post>>(POST_DIFF) {

    override fun getItemViewType(position: Int): Int {

        val item = getItem(position)

        return when {
            item.secureMedia?.redditVideo != null -> R.layout.post_item_secure_media
            item.secureMedia?.youtubeoEmbed != null -> R.layout.post_item_secure_media
            item.post_hint == "image" -> R.layout.post_item_image
            item.post_hint == "link" -> R.layout.post_item_link
            else -> R.layout.post_item_text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Post> {
        return when (viewType) {
            R.layout.post_item_secure_media ->
                PostWithSecureMediaViewHolder(parent, onPostClickHandler)
            R.layout.post_item_image -> PostWithImageViewHolder(parent, onPostClickHandler)
            R.layout.post_item_link -> PostWithLinkViewHolder(parent, onPostClickHandler)
            else -> PostTextOnlyViewHolder(parent, onPostClickHandler)
        }
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<Post>, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val POST_DIFF = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }
}
