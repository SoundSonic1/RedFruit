package com.example.redfruit.ui.home.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemYoutubeBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.home.fragment.YoutubeFragment
import com.example.redfruit.util.Constants
import com.example.redfruit.util.addOrShowFragment
import kotlinx.android.synthetic.main.post_item_youtube.view.*

class PostWithYoutubeViewHolder(
    parent: ViewGroup,
    private val fm: FragmentManager,
    private val binding: PostItemYoutubeBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.post_item_youtube,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post, listener: (Post) -> Unit) {
        binding.item = item
        itemView.postTitleAuthorIncludeYoutube.setOnClickListener {
            listener(item)
        }
        itemView.postYoutubeImageView.setOnClickListener {
            addOrShowFragment(
                fm,
                R.id.mainContent,
                YoutubeFragment.newInstance(item.secureMedia?.youtubeoEmbed?.youtubeId ?: ""),
                Constants.YOUTUBE_FRAGMENT_TAG
            )
        }
    }
}
