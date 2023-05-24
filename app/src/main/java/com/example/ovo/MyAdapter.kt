package com.example.ovo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/* gridview adapter */
class MyAdapter(context: Context, data: ArrayList<Note>, private val layout: Int) : ArrayAdapter<Note>(context, layout, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = View.inflate(parent.context, layout, null)
        val item = getItem(position) ?: return view
        val title = view.findViewById<TextView>(R.id.title)
        title.setPadding(15, 0, 0,0)

        val content = view.findViewById<TextView>(R.id.content)

        /* show part of note */
        title.text = item.title
        content.text = item.content

        return view
    }
}