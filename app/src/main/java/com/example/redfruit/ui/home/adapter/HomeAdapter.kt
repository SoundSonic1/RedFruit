package com.example.redfruit.ui.home.adapter

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.base.GenericAdapter
import com.example.redfruit.ui.home.adapter.viewholder.PostTextOnlyViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithImageViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithRedditVideoViewHolder
import com.example.redfruit.ui.home.adapter.viewholder.PostWithYoutubeViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Adapter for the RecyclerView of the SubredditPostsFragment
 * @property fm FragmentManager to create new fragments on click events
 */
class HomeAdapter(
    private val fm : FragmentManager,
    items: MutableList<Post>,
    listener: (Post) -> Unit
) : GenericAdapter<Post>(items, listener) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Post> {
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

    /**
     * Identify posts that contain image(s)
     */
    override fun getLayoutId(position: Int, obj: Post): Int  {
        // TODO: support more oEmbed
        if (obj.secureMedia?.youtubeoEmbed?.provider_url == "https://www.youtube.com/") {
            return R.layout.post_item_youtube
        }
        else if (obj.secureMedia?.redditVideo != null) {
            return R.layout.post_item_reddit_video
        }
        else if (obj.preview.images.isNotEmpty()) {
            return R.layout.post_item_image
        }
        return R.layout.post_item_text
    }

    override fun getItemId(position: Int) = listItems[position].id.hashCode().toLong()

    fun notifyChanges(newList: List<Post>) =
        CoroutineScope(Dispatchers.Main).launch {
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
}
