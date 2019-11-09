package com.example.redfruit.ui.comments.groupie

import android.view.LayoutInflater
import android.view.View
import com.example.redfruit.R
import com.example.redfruit.data.model.Comment
import com.example.redfruit.databinding.CommentItemBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.comment_item.view.*

/**
 * Item defines layout for GroupAdapter
 */
class CommentItem(
    private val comment: Comment
) : BindableItem<CommentItemBinding>(), ExpandableItem {

    private var listener: ExpandableGroup? = null

    override fun bind(viewBinding: CommentItemBinding, position: Int) {

        viewBinding.apply {
            item = comment
            clickListener = listener
        }

        addSeparatorToView(viewBinding.root)
    }

    private fun addSeparatorToView(view: View) {
        val container = view.comment_item_linear_layout

        // remove old separators
        for (i in 0..container.childCount - 2) {
            container.removeViewAt(0)
        }

        // add new separators to visualize the depth
        for (i in 1..comment.depth) {
            val separator = LayoutInflater
                .from(view.context)
                .inflate(R.layout.item_separator, container, false)

            container.addView(separator, 0)
        }
    }

    override fun getLayout(): Int = R.layout.comment_item

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        listener = onToggleListener
    }
}
