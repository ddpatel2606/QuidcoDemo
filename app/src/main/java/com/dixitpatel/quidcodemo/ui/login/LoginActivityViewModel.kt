package com.dixitpatel.quidcodemo.ui.login

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dixitpatel.quidcodemo.constant.API_KEY
import com.dixitpatel.quidcodemo.network.APIRequestResponseHandler
import com.dixitpatel.quidcodemo.network.ApiInterface
import com.dixitpatel.quidcodemo.ui.login.model.LoginOutPutModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor() : ViewModel()
{

    private val _isFormValid = MutableLiveData<Boolean>()

    val isValid: LiveData<Boolean>
        get() = _isFormValid

    var username = ""
        set(value) {
            field = value
            validateForm()
        }

    var password = ""
        set(value) {
            field = value
            validateForm()
        }

    private fun validateForm() {
        if (username.length > 1 && password.length > 1) {
            _isFormValid.postValue(true)
        } else {
            _isFormValid.postValue(false)
        }
    }

    private val loginApiResponse = MutableLiveData<APIRequestResponseHandler<LoginOutPutModel?>>()

    fun getLoginApiResult(): MutableLiveData<APIRequestResponseHandler<LoginOutPutModel?>> = loginApiResponse

    fun loginApiCall(method:String, username:String, password:String, apiSignature:String, format:String, apiInterface : ApiInterface)
    {
        loginApiResponse.value = APIRequestResponseHandler.loading(null)

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<LoginOutPutModel> =
                apiInterface.loginAPI(method, username, password, API_KEY, apiSignature, format)!!

            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            loginApiResponse.value = APIRequestResponseHandler.success(it)

                            Timber.e(response.body().toString())
                        }
                    } else {
                        loginApiResponse.value = APIRequestResponseHandler.error(null,response.errorBody().toString())
                        Timber.e(response.errorBody().toString())
                    }
                }
            } catch (e: HttpException) {
                Timber.e("Exception ${e.message}")
                loginApiResponse.value = APIRequestResponseHandler.error(null,e.message.toString())
            } catch (e: Throwable) {
                Timber.e("Exception ${e.message}")
                loginApiResponse.value = APIRequestResponseHandler.error(null,e.message.toString())
            }
           }
    }
}