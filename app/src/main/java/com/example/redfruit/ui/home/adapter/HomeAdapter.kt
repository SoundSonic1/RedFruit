package com.example.redfruit.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostItemBinding
import com.example.redfruit.databinding.PostItemWithImageBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.base.GenericAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Adapter for the RecyclerView of the HomeFragment
 * @property uiScope required to make updates to the UI after async diff calc
 */
class HomeAdapter(private val uiScope: CoroutineScope, items: MutableList<Post>,
                  listener: (Post) -> Unit) : GenericAdapter<Post>(items, listener) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Post> {
        if (viewType == viewTypeWithImage) {
            return PostWithImageViewHolder(parent)
        }
        return PostTextOnlyViewHolder(parent)
    }

    /**
     * Identify posts that contain image(s)
     */
    override fun getLayoutId(position: Int, obj: Post): Int  {
        if (obj.preview.images.isNotEmpty()) {
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
    }

}

/**
 * Manage the individual items with data binding
 */
class PostWithImageViewHolder(
    parent: ViewGroup,
    private val binding: PostItemWithImageBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.post_item_with_image,
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

class PostTextOnlyViewHolder(
    parent: ViewGroup,
    private val binding: PostItemBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.post_item,
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
