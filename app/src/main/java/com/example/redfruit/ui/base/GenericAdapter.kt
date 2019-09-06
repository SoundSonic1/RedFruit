package com.example.redfruit.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Basic adapter that handles most of the RecyclerView adapter logic with Generics.
 * @author https://medium.com/@mohanmanu/generic-adapter-with-kotlin-part-i-74e191d68b0f
 */
abstract class GenericAdapter<T>(private val listItems: List<T>,
                                 private val listener: (T) -> Unit) : RecyclerView.Adapter<AbstractViewHolder<T>>()
{
    override fun getItemCount() = listItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<T> {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {
        holder.bind(listItems[position], listener)
    }

    override fun getItemViewType(position: Int) = getLayoutId(position, listItems[position])

    /**
     * @param position for more complex layouts
     * @param obj for multitypeViews
     */
    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<T>

}