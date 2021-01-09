package com.dixitpatel.quidcodemo.ui.base

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerAppCompatActivity

/**
 *  Base Activity : all activity will extend this and pass their ViewModel object as Generic type.
 */
abstract class BaseActivity<out T : ViewModel?> : DaggerAppCompatActivity() {

    lateinit var me: BaseActivity<*>
    private var viewModel: T? = null

    /**
     * @return view model instance
     */
    abstract fun getViewModel(): T

    fun startActivity(
        viewStart: View?,
        transactionName: String?,
        intent: Intent?
    ) {
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewStart!!, transactionName!!)
        ActivityCompat.startActivity(this, intent!!, options.toBundle())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            me = this
            viewModel = if (viewModel == null) getViewModel() else viewModel
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }


}