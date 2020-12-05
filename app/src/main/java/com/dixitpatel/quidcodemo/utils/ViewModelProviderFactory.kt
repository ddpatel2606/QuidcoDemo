package com.dixitpatel.quidcodemo.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelProviderFactory<V>(private val viewModel: V) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((viewModel as ViewModel).javaClass)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}