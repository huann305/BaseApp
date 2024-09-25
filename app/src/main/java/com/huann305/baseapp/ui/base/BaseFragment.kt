package com.huann305.baseapp.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    abstract val TAG: String
    abstract fun layoutRes(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun initEvent()

    lateinit var binding: T
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity !is BaseActivity<*>) {
            Throwable("Activity no override BaseActivity")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindView(inflater, container, layoutRes())
        return binding.root
    }

    private fun bindView(inflater: LayoutInflater?, viewGroup: ViewGroup?, res: Int) {
        binding = DataBindingUtil.inflate(
            inflater!!, res, viewGroup, false
        )
        context = requireContext()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        initView()
    }

    protected fun showToast(mess: String?) {
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()
    }
}