package com.huann305.baseapp.ui.screen.main.view.activity

import android.util.Log
import androidx.lifecycle.Observer
import com.huann305.baseapp.R
import com.huann305.baseapp.data.local.dao.IDao
import com.huann305.baseapp.data.local.db.AppDatabase
import com.huann305.baseapp.data.model.Cat
import com.huann305.baseapp.data.model.Item
import com.huann305.baseapp.data.remote.CatRepository
import com.huann305.baseapp.databinding.ActivityMainBinding
import com.huann305.baseapp.ui.base.BaseActivity
import com.huann305.baseapp.ui.screen.main.adapter.MainAdapter
import com.huann305.baseapp.ui.screen.main.viewmodel.MyViewModel
import com.huann305.baseapp.util.getViewModel
import kotlin.random.Random

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val catRepository = CatRepository()

    override var TAG: String
        get() = "MainActivity"
        set(value) {}

    lateinit var adapter: MainAdapter

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getDataFromIntent() {
        showLog("getDataFromIntent")
    }
    private lateinit var myViewModel: MyViewModel
    override fun initData() {
        fetchCuteCats()


        adapter = object : MainAdapter(this){
            override fun onDelete(item: Item) {
                myViewModel.delete(item)
            }

        }
        binding.rcv.adapter = adapter
        val dao: IDao = AppDatabase.getInstance(this).iDao()

        myViewModel = getViewModel(MyViewModel::class.java) { MyViewModel(dao) }

        myViewModel.list.observe(this) { items ->
            adapter.setList(items)
        }

        setOnClick(R.id.btn){
            myViewModel.insert(Item(name = getRandomName(Random.nextInt(2, 7))))
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

    private fun fetchCuteCats() {
        catRepository.getCatsByTag("cute", object : CatRepository.CatCallback {
            override fun onSuccess(cats: List<Cat>) {
                cats.forEach {
                    Log.d("CatAPI", "Cat ID: ${it.id}}")
                }
            }

            override fun onError(errorMessage: String) {
                Log.e("CatAPI", "Error: $errorMessage")
            }
        })
    }
}