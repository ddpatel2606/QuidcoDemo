package com.dixitpatel.quidcodemo.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerDialogFragment

abstract class BaseDialogFragment<T : ViewModel?> :
    DaggerDialogFragment() {
    private var viewModel: T? = null

    /**
     * @return view model instance
     */
    abstract fun getViewModel(): T
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = getViewModel()
    }

    override fun onDetach() {
        super.onDetach()
    }
}