package com.huann305.baseapp.ui.screen.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huann305.baseapp.R
import com.huann305.baseapp.data.model.Item
import com.huann305.baseapp.util.onClick

abstract class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
    private var list: List<Item> = listOf()
    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv = itemView.findViewById<TextView>(R.id.name)
        fun bindView(item: Item){
            tv.text = item.name + item.id

            itemView.onClick {
                onDelete(item)
            }
        }
    }

    fun setList(list: List<Item>){
        this.list = list
        notifyDataSetChanged()
    }

    abstract fun onDelete(item: Item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_main, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = list[position]

        holder.bindView(item)
    }
}