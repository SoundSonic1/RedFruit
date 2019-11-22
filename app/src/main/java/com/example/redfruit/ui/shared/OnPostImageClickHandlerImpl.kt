package com.example.redfruit.ui.shared

import androidx.fragment.app.FragmentManager
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.media.fragment.ImageFragment
import com.example.redfruit.ui.media.fragment.StreamVideoFragment
import com.example.redfruit.ui.media.fragment.YoutubeFragment
import com.example.redfruit.util.addOrShowFragment

class OnPostImageClickHandlerImpl(
    private val fm: FragmentManager
) : OnPostImageClickHandler {

    override fun onClick(post: Post) {
        when {
            post.secureMedia?.redditVideo != null -> addOrShowFragment(
                fm,
                R.id.mainContent,
                StreamVideoFragment.newInstance(post.secureMedia.redditVideo.url)
            )
            post.secureMedia?.youtubeoEmbed != null -> addOrShowFragment(
                fm,
                R.id.mainContent,
                YoutubeFragment.newInstance(post.secureMedia.youtubeoEmbed.youtubeId)
            )
            post.preview.enabled -> addOrShowFragment(
                fm,
                R.id.mainContent,
                ImageFragment.newInstance(post.preview.firstImage?.source?.url ?: "")
            )
        }
    }
}
