package com.huann305.baseapp.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding> :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>>() {

    protected var items: MutableList<T> = mutableListOf()

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): VB

    abstract fun bindItem(binding: VB, item: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = createBinding(LayoutInflater.from(parent.context), parent)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bindItem(holder.binding, items[position], position)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
    fun setList(newItems: List<T>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}