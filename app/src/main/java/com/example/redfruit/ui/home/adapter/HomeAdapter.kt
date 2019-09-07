package com.example.redfruit.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.databinding.PostViewBinding
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.base.GenericAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Adapter for the RecyclerView of the HomeFragment
 */
class HomeAdapter(items: MutableList<Post>,
                  listener: (Post) -> Unit) : GenericAdapter<Post>(items, listener) {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun getViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent)

    override fun getLayoutId(position: Int, obj: Post) = R.layout.post_view

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

}

/**
 * Manages the individual items with data binding
 */
class PostViewHolder(
    parent: ViewGroup,
    private val binding: PostViewBinding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.post_view,
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