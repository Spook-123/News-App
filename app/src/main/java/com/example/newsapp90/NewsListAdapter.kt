package com.example.newsapp90

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(val listener:NewsItemClicked): RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {


    private val items:ArrayList<News> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.tvTitle.text = currentItem.title
        holder.tvAuthor.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews:ArrayList<News>) {
        items.clear()

        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvAuthor = itemView.findViewById<TextView>(R.id.tvAuthor)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }
}


interface NewsItemClicked {
    fun onItemClicked(item:News)
}