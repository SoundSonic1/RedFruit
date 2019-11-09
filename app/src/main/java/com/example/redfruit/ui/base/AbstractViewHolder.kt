package com.example.redfruit.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Ensures that ViewHolder used in our adapters have a bind method
 */
abstract class AbstractViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T, listener: (T) -> Unit)
}
