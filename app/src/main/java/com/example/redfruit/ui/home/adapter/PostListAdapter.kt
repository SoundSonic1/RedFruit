package com.example.redfruit.ui.home.adapter

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostTextOnlyViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithImageViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithRedditVideoViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithYoutubeViewHolder

/**
 * Adapter for SubredditPostsFragment
 */
class PostListAdapter(
    private val fm: FragmentManager,
    private val listener: (Post) -> Unit
) : ListAdapter<Post, AbstractViewHolder<Post>>(POST_DIFF) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        // TODO: support more oEmbed
        if (item.secureMedia?.youtubeoEmbed?.provider_url == "https://www.youtube.com/") {
            return R.layout.post_item_youtube
        }
        else if (item.secureMedia?.redditVideo != null) {
            return R.layout.post_item_reddit_video
        }
        else if (item.preview.images.isNotEmpty()) {
            return R.layout.post_item_image
        }
        return R.layout.post_item_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Post> {
        if (viewType == R.layout.post_item_youtube) {
            return PostWithYoutubeViewHolder(parent, fm)
        }
        else if (viewType == R.layout.post_item_reddit_video) {
            return PostWithRedditVideoViewHolder(parent, fm)
        }
        else if (viewType == R.layout.post_item_image) {
            return PostWithImageViewHolder(parent, fm)
        }
        return PostTextOnlyViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<Post>, position: Int) {
        holder.bind(getItem(position), listener)
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