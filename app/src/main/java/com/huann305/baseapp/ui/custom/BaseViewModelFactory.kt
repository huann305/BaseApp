package com.huann305.baseapp.ui.custom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BaseViewModelFactory<VM : ViewModel>(
    private val viewModelClass: Class<VM>,
    private val creator: () -> VM
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            @Suppress("UNCHECKED_CAST")
            return creator() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

