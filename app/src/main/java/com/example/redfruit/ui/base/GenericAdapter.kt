package com.example.redfruit.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Basic adapter that handles most of the RecyclerView adapter logic with Generics.
 * @property listItems data which populates the RecyclerView
 * @property listener to translate user actions
 * @author https://medium.com/@mohanmanu/generic-adapter-with-kotlin-part-i-74e191d68b0f
 */
abstract class GenericAdapter<T>(
    protected val listItems: MutableList<T>,
    private val listener: (T) -> Unit) : RecyclerView.Adapter<AbstractViewHolder<T>>() {

    override fun getItemCount() = listItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<T> {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<T>, position: Int) {
        holder.bind(listItems[position], listener)
    }

    override fun getItemViewType(position: Int) = getLayoutId(position, listItems[position])

    /**
     * return different layout ids if working with multi view types
     */
    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<T>

}