package com.example.redfruit.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.redfruit.R
import com.example.redfruit.data.model.Post
import kotlinx.android.synthetic.main.post_view.view.*

class HomeAdapter(private val listener: (Post) -> Unit) : RecyclerView.Adapter<PostViewHolder>() {

    private val items = mutableListOf<Post>()

    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        // Binds each post in the List to a view
        holder.title.text = items[position].title
        holder.bind(items[position], listener)
    }

    fun refreshData(items: List<Post>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each post to
    internal val title = view.postView
    fun bind(pos: Post, listener: (Post) -> Unit) = with(itemView) {
        itemView.setOnClickListener {
            listener(pos)
        }
    }
}