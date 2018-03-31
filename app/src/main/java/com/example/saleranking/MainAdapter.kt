package com.example.saleranking

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row.view.*

class MainAdapter(): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))
    }

    override fun getItemCount(): Int {
        return 30
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.itemView.textView.text = "ポケモンGO"
    }

    class MainHolder(view: View): RecyclerView.ViewHolder(view)
}
