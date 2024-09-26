package com.huann305.baseapp.ui.screen.main.view.activity

import android.util.Log
import com.huann305.baseapp.R
import com.huann305.baseapp.data.local.dao.IDao
import com.huann305.baseapp.data.local.db.AppDatabase
import com.huann305.baseapp.data.model.Cat
import com.huann305.baseapp.data.model.Item
import com.huann305.baseapp.data.repo.ItemRepository
import com.huann305.baseapp.databinding.ActivityMainBinding
import com.huann305.baseapp.ui.base.BaseActivity
import com.huann305.baseapp.ui.screen.main.adapter.MainAdapter
import com.huann305.baseapp.ui.screen.main.viewmodel.MyViewModel
import com.huann305.baseapp.util.getViewModel
import com.huann305.baseapp.util.isNetworkAvailable
import kotlin.random.Random

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override var TAG: String
        get() = "MainActivity"
        set(value) {}

    lateinit var adapter: MainAdapter

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getDataFromIntent() {
        showLog("getDataFromIntent")
        showLog("${isNetworkAvailable(this)}")
    }

    private lateinit var myViewModel: MyViewModel
    override fun initData() {
        adapter = object : MainAdapter() {
            override fun onItemClick(item: Item, position: Int) {
                myViewModel.delete(item)
            }
        }

        binding.rcv.adapter = adapter
        val database = AppDatabase.getInstance(this)
        val repo = ItemRepository(database)

        myViewModel = getViewModel(MyViewModel::class.java) { MyViewModel(repo) }

        myViewModel.list.observe(this) { items ->
            adapter.setList(items)
        }

        setOnClick(R.id.btn) {
            myViewModel.insert(Item("", getRandomName(4)))
        }
    }

    fun getRandomName(length: Int): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        return (1..length)
            .map { alphabet.random() }
            .joinToString("")
    }

    override fun initView() {
    }

    override fun initEvent() {
    }

}