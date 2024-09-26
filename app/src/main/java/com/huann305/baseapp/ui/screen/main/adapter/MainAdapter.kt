package com.huann305.baseapp.ui.screen.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.huann305.baseapp.data.model.Item
import com.huann305.baseapp.databinding.LayoutItemMainBinding
import com.huann305.baseapp.ui.base.BaseAdapter

abstract class MainAdapter : BaseAdapter<Item, LayoutItemMainBinding>() {
    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): LayoutItemMainBinding {
        return LayoutItemMainBinding.inflate(inflater, parent, false)
    }

    abstract fun onItemClick(item: Item, position: Int)

    override fun bindItem(binding: LayoutItemMainBinding, item: Item, position: Int) {
        binding.name.text = item.name + " " + item.id
        binding.root.setOnClickListener {
            onItemClick(item, position)
        }
    }
}