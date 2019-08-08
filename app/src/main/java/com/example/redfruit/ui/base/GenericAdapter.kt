package com.example.redfruit.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Basic adapter that handles most of the RecyclerView adapter logic with Generics.
 * @author https://medium.com/@mohanmanu/generic-adapter-with-kotlin-part-i-74e191d68b0f
 */
abstract class GenericAdapter<T>(private val listItems: MutableList<T>,
                                 private val context: Context,
                                 private val listener: (T) -> Unit) : RecyclerView.Adapter<AbstractViewHolder<T>>()
{
    override fun getItemCount() = listItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<T> {
        return getViewHolder(
            LayoutInflater.from(context)
            .inflate(viewType, parent, false), viewType)
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {
        holder.bind(listItems[position], listener)
    }

    override fun getItemViewType(position: Int) = getLayoutId(position, listItems[position])

    fun refreshItems(listItems: Collection<T>) {
        this.listItems.clear()
        this.listItems.addAll(listItems)
        notifyDataSetChanged()
    }

    /**
     * @param position for more complex layouts
     * @param obj for multitypeViews
     */
    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(view: View, viewType: Int): AbstractViewHolder<T>

}