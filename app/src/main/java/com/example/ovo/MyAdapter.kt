package com.example.ovo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val data:ArrayList<Note>, private val layout: Int) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private lateinit var myListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        myListener = listener
    }

    class ViewHolder(v:View, listener: onItemClickListener): RecyclerView.ViewHolder(v) {
        val title = v.findViewById<TextView>(R.id.title)
        val content = v.findViewById<TextView>(R.id.content)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                listener.onItemLongClick(adapterPosition)
                return@setOnLongClickListener(true)
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
        return ViewHolder(v, myListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Note(data[position].title, data[position].content)

        holder.title.text = item.title
        holder.content.text = item.content
    }
}