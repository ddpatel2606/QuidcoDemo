package com.dixitpatel.quidcodemo.ui.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.dixitpatel.quidcodemo.R
import com.dixitpatel.quidcodemo.databinding.ActivityDetailViewBinding
import com.dixitpatel.quidcodemo.network.ApiInterface
import com.dixitpatel.quidcodemo.ui.base.BaseActivity
import com.dixitpatel.quidcodemo.utils.Alerts
import javax.inject.Inject

class DetailViewActivity : BaseActivity<DetailViewModel>()
{
    private lateinit var binding: ActivityDetailViewBinding

    companion object
    {
         var URL = "selection_url"
         var SELCTION_TITLE = "selection_title"
    }

    @Inject
    lateinit var apiInterface : ApiInterface

    @Inject
    lateinit var models: DetailViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_view)

        setSupportActionBar(binding.toolbar)
        // enabling action bar app icon and behaving it as toggle button
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_material);
        supportActionBar?.title = intent.getStringExtra(SELCTION_TITLE)
        binding.toolbar.title = intent.getStringExtra(SELCTION_TITLE)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.webView.settings.defaultZoom = WebSettings.ZoomDensity.FAR
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
            {
                super.onPageStarted(view, url, favicon)
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {

                Alerts.showSnackBar(
                    this@DetailViewActivity, "Error: $description")
            }

            override fun onPageFinished(view: WebView, url: String) {
                Alerts.dismissProgressBar()
            }
        }
        if (intent.getStringExtra(URL) != null && intent.getStringExtra(URL)!!.isNotEmpty()) {
            binding.webView.loadUrl(intent.getStringExtra(URL))
            Alerts.showProgressBar(this)
        } else {
            Alerts.showSnackBar(
                this@DetailViewActivity, getString(R.string.something_went_wrong))
        }
    }

    override fun getViewModel(): DetailViewModel {
        return models
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            binding.webView.clearHistory()
            super.onBackPressed()
        }
    }

}