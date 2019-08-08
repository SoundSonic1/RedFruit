package com.example.redfruit.ui.home.adapter

import android.content.Context
import android.view.View
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import com.example.redfruit.ui.base.AbstractViewHolder
import com.example.redfruit.ui.base.GenericAdapter
import kotlinx.android.synthetic.main.post_view.view.*

class HomeAdapter(items: MutableList<Post>,
                  context: Context,
                  listener: (Post) -> Unit) : GenericAdapter<Post>(items, context, listener) {

    override fun getViewHolder(view: View, viewType: Int) = PostViewHolder(view)

    override fun getLayoutId(position: Int, obj: Post) = R.layout.post_view

}

/**
 * Manages the individual items with bind()
 */
class PostViewHolder(view: View) : AbstractViewHolder<Post>(view) {
    // get the TextView
    private val titleView = itemView.postView
    override fun bind(item: Post, listener: (Post) -> Unit) = with(itemView) {
        // set the text in TextView and add a listener
        titleView.text = item.title
        itemView.setOnClickListener {
            listener(item)
        }
    }
}