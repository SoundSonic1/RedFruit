package com.example.redfruit.ui.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemSecureMediaBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.media.fragment.StreamVideoFragment
import com.example.redfruit.ui.media.fragment.YoutubeFragment
import com.example.redfruit.util.addOrShowFragment
import kotlinx.android.synthetic.main.post_item_secure_media.view.postExoPlayerImageView
import kotlinx.android.synthetic.main.post_item_secure_media.view.postTitleAuthorIncludeRedditVideo

class PostWithSecureMediaViewHolder(
    parent: ViewGroup,
    private val fm: FragmentManager,
    private val binding: PostItemSecureMediaBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.post_item_secure_media,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post, listener: (Post) -> Unit) {

        binding.item = item

        itemView.postTitleAuthorIncludeRedditVideo.setOnClickListener {
            listener(item)
        }

        itemView.postExoPlayerImageView.setOnClickListener {

            if (item.secureMedia?.youtubeoEmbed?.provider_url == "https://www.youtube.com/") {
                addOrShowFragment(
                    fm,
                    R.id.mainContent,
                    YoutubeFragment.newInstance(item.secureMedia.youtubeoEmbed.youtubeId)
                )
            } else {
                addOrShowFragment(
                    fm,
                    R.id.mainContent,
                    StreamVideoFragment.newInstance(item.secureMedia?.redditVideo?.url ?: "")
                )
            }
        }
    }
}
