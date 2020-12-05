package com.dixitpatel.quidcodemo.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dixitpatel.quidcodemo.R
import com.dixitpatel.quidcodemo.constant.LOGIN_METHOD
import com.dixitpatel.quidcodemo.databinding.ActivityLoginBinding
import com.dixitpatel.quidcodemo.network.APIRequestResponseHandler
import com.dixitpatel.quidcodemo.network.ApiInterface
import com.dixitpatel.quidcodemo.network.AuthStatus
import com.dixitpatel.quidcodemo.prefs.PrefEntity
import com.dixitpatel.quidcodemo.prefs.Preferences
import com.dixitpatel.quidcodemo.ui.base.BaseActivity
import com.dixitpatel.quidcodemo.ui.main.MainActivity
import com.dixitpatel.quidcodemo.utils.Alerts
import com.dixitpatel.quidcodemo.utils.Utils
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginActivityViewModel?>(), LoginActivityEventHandler{

    lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var apiInterface: ApiInterface

    @Inject
    lateinit var model: LoginActivityViewModel

    override fun getViewModel(): LoginActivityViewModel {
        return model
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = model
        binding.lifecycleOwner = this
        binding.clickHandler = this

        binding.tieUserName.doAfterTextChanged { text -> model.username = text?.toString() ?: "" }
        binding.tiePassword.doAfterTextChanged { text -> model.password = text?.toString() ?: "" }

        model.isValid.observe(this, Observer { valid ->
            binding.signInBtn.isEnabled = valid ?: false
        })

        model.getLoginApiResult().observe(
                this, androidx.lifecycle.Observer
            { result ->

                    when (result.status) {
                        AuthStatus.LOADING -> {
                            Alerts.showProgressBar(this@LoginActivity)

                        }
                        AuthStatus.ERROR -> {

                            Alerts.dismissProgressBar()
                            Alerts.showSnackBar(this@LoginActivity, result.message)

                        }
                        AuthStatus.SUCCESS -> {

                            Alerts.dismissProgressBar()

                            Preferences.setPreference(PrefEntity.AUTH_TOKEN, result.data!!.session!!.key)
                            Preferences.setPreference(PrefEntity.USER_NAME, result.data.session!!.name)

                            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                            this.finish()
                        }
                    }
                })
    }

    override fun onLoginClicked() {
        if(Utils.isNetworkAvailable(this@LoginActivity))
        {
            this.let {

                if(binding.tieUserName.text.toString().trim().isNotEmpty() && binding.tiePassword.text.toString().trim().isNotEmpty()) {

                    apiInterface.let {
                        model.loginApiCall(
                            LOGIN_METHOD,
                            binding.tieUserName.text.toString().trim(),
                            binding.tiePassword.text.toString().trim(),
                            Utils.genMethodSignature(
                                binding.tieUserName.text.toString().trim(),
                                binding.tiePassword.text.toString().trim()
                            ),
                            "json",
                            apiInterface
                        )
                    }
                }else{
                    Alerts.showSnackBar(
                        this@LoginActivity,
                        getString(R.string.fields_cant_be_empty)
                    )
                }
            }

        }
        else{
            Alerts.showSnackBar(
                this@LoginActivity,
                getString(R.string.internet_not_available)
            )
        }
    }
}