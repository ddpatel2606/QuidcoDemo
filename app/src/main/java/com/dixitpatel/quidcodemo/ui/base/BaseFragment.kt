package com.dixitpatel.quidcodemo.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerFragment

/**
 *  Base Fragment : all fragments will extend this and pass their ViewModel object as Generic type.
 */
abstract class BaseFragment<out T : ViewModel?> : DaggerFragment() {
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