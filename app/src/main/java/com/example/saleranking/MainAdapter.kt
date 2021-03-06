package com.example.saleranking

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row.view.*

class MainAdapter(private val results: Array<Result>): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.itemView.textView.text = results[position].name
        Glide.with(holder.itemView).load(results[position].artworkUrl100).into(holder.itemView.imageView)
    }

    class MainHolder(view: View): RecyclerView.ViewHolder(view)
}
