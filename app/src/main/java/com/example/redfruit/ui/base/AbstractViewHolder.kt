package com.example.redfruit.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Ensures that the GenericAdapter can bind the ViewHolder and the data
 */
abstract class AbstractViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T, listener: (T) -> Unit)
}