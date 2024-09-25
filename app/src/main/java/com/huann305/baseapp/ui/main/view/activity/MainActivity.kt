package com.huann305.baseapp.ui.main.view.activity

import android.util.Log
import androidx.lifecycle.Observer
import com.huann305.baseapp.R
import com.huann305.baseapp.data.local.dao.IDao
import com.huann305.baseapp.data.local.db.AppDatabase
import com.huann305.baseapp.data.model.Cat
import com.huann305.baseapp.data.remote.CatRepository
import com.huann305.baseapp.databinding.ActivityMainBinding
import com.huann305.baseapp.ui.base.BaseActivity
import com.huann305.baseapp.ui.main.viewmodel.MyViewModel
import com.huann305.baseapp.util.getViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val catRepository = CatRepository()

    override var TAG: String
        get() = "MainActivity"
        set(value) {}

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getDataFromIntent() {
        showLog("getDataFromIntent")
    }
    private lateinit var myViewModel: MyViewModel
    override fun initData() {
        fetchCuteCats()

        val dao: IDao = AppDatabase.getInstance(this).iDao()

        myViewModel = getViewModel(MyViewModel::class.java) { MyViewModel(dao) }

        myViewModel.list.observe(this, Observer { items ->
            for (i in items) {
                showLog("${i.name}")
            }
        })
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