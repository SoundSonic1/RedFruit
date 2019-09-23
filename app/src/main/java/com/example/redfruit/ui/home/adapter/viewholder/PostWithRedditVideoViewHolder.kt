package com.example.redfruit.ui.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemRedditVideoBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.media.fragment.StreamVideoFragment
import com.example.redfruit.util.Constants
import com.example.redfruit.util.addOrShowFragment
import kotlinx.android.synthetic.main.post_item_reddit_video.view.*

class PostWithRedditVideoViewHolder(
    parent: ViewGroup,
    private val fm: FragmentManager,
    private val binding: PostItemRedditVideoBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.post_item_reddit_video,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {
    override fun bind(item: Post, listener: (Post) -> Unit) {
        binding.item = item
        itemView.postTitleAuthorIncludeRedditVideo.setOnClickListener {
            listener(item)
        }
        itemView.postExoPlayerImageView.setOnClickListener {

            addOrShowFragment(
                fm,
                R.id.mainContent,
                StreamVideoFragment.newInstance(item.secureMedia?.redditVideo?.url ?: ""),
                Constants.STREAM_VIDEO_FRAGMENT_TAG
            )
        }
    }
}
