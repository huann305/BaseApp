package com.huann305.baseapp.ui.screen.main.view.fragment

import com.huann305.baseapp.R
import com.huann305.baseapp.databinding.FragmentBlankBinding
import com.huann305.baseapp.ui.base.BaseFragment

class BlankFragment : BaseFragment<FragmentBlankBinding>() {
    override val TAG: String
        get() = "BlankFragment"

    override fun layoutRes(): Int {
        return R.layout.fragment_blank
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }
}