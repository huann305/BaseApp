package com.huann305.baseapp.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.huann305.baseapp.R

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract var TAG: String
    abstract fun getLayoutRes(): Int
    abstract fun getDataFromIntent(): Unit
    abstract fun initData()
    abstract fun initView()
    abstract fun initEvent()
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutRes())

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        doFirstMethod()
        getDataFromIntent()
        initData()
        initView()
        initEvent()
    }

    protected fun doFirstMethod() {

    }

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showLog(message: String) {
        Log.e(TAG, message)
    }

    protected fun setOnClick(viewId: Int, onClick: () -> Unit) {
        findViewById<View>(viewId).setOnClickListener { onClick() }
    }
}