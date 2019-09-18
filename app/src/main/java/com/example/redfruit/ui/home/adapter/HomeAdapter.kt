package com.example.redfruit.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemImageBinding
import com.example.redfruit.databinding.PostItemRedditVideoBinding
import com.example.redfruit.databinding.PostItemTextBinding
import com.example.redfruit.databinding.PostItemYoutubeBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.base.GenericAdapter
import kotlinx.android.synthetic.main.post_item_youtube.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Adapter for the RecyclerView of the SubredditPostsFragment
 * @property uiScope required to make updates to the UI after async diff calc
 * @property lifeCycle to properly release YoutubePlayerView
 */
class HomeAdapter(
    items: MutableList<Post>,
    private val uiScope: CoroutineScope,
    private val lifeCycle: Lifecycle,
    listener: (Post) -> Unit
) : GenericAdapter<Post>(items, listener) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Post> {
        if (viewType == viewTypeWithYoutube) {
            return PostWithYoutubeViewHolder(parent, lifeCycle)
        }
        else if (viewType == viewTypeWithMedia) {
            return PostWithRedditVideoViewHolder(parent)
        }
        else if (viewType == viewTypeWithImage) {
            return PostWithImageViewHolder(parent)
        }
        return PostTextOnlyViewHolder(parent)
    }

    /**
     * Identify posts that contain image(s)
     */
    override fun getLayoutId(position: Int, obj: Post): Int  {
        // TODO: support more oEmbed
        if (obj.secureMedia?.youtubeoEmbed?.provider_url == "https://www.youtube.com/") {
            return viewTypeWithYoutube
        }
        else if (obj.secureMedia?.redditVideo != null) {
            return viewTypeWithMedia
        }
        else if (obj.preview.images.isNotEmpty()) {
            return viewTypeWithImage
        }
        return viewTypeTextOnly
    }

    override fun getItemId(position: Int) = listItems[position].id.hashCode().toLong()

    fun notifyChanges(newList: List<Post>) =
        uiScope.launch {
            val diff = calculateDiff(listItems, newList)
            listItems.clear()
            listItems.addAll(newList)
            diff.dispatchUpdatesTo(this@HomeAdapter)
        }

    private suspend fun calculateDiff(oldList: List<Post>, newList: List<Post>) =
        withContext(Dispatchers.Default) {
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition].id == newList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition] == newList[newItemPosition]
                }

                override fun getOldListSize() = oldList.size

                override fun getNewListSize() = newList.size
            })

        }

    companion object {
        private const val viewTypeTextOnly = 0
        private const val viewTypeWithImage = 1
        private const val viewTypeWithMedia = 2
        private const val viewTypeWithYoutube = 3
    }

}

/**
 * Manage the individual items with data binding
 */
class PostWithImageViewHolder(
    parent: ViewGroup,
    private val binding: PostItemImageBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.post_item_image,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post, listener: (Post) -> Unit) = with(itemView) {
        // set the text in TextView, fetch image for ImageView and add a listener
        binding.item = item
        itemView.setOnClickListener {
            listener(item)
        }
    }
}

class PostWithRedditVideoViewHolder(
    parent: ViewGroup,
    private val binding: PostItemRedditVideoBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.post_item_reddit_video,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {
    override fun bind(item: Post, listener: (Post) -> Unit) {
        binding.item = item
    }
}

class PostWithYoutubeViewHolder(
    parent: ViewGroup,
    lifeCycle: Lifecycle,
    private val binding: PostItemYoutubeBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.post_item_youtube,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {
    init {
        lifeCycle.addObserver(itemView.postYoutubeView)
    }
    override fun bind(item: Post, listener: (Post) -> Unit) {
        binding.item = item
    }
}

class PostTextOnlyViewHolder(
    parent: ViewGroup,
    private val binding: PostItemTextBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.post_item_text,
            parent, false)
) : AbstractViewHolder<Post>(binding.root) {

    override fun bind(item: Post, listener: (Post) -> Unit) = with(itemView) {
        // set the text in TextView, fetch image for ImageView and add a listener
        binding.item = item
        itemView.setOnClickListener {
            listener(item)
        }
    }
}
