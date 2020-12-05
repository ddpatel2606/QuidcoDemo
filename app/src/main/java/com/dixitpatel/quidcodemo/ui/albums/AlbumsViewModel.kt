package com.dixitpatel.quidcodemo.ui.albums

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dixitpatel.quidcodemo.constant.API_KEY
import com.dixitpatel.quidcodemo.network.APIRequestResponseHandler
import com.dixitpatel.quidcodemo.network.ApiInterface
import com.dixitpatel.quidcodemo.ui.albums.model.GetAlbumOutputModel
import com.dixitpatel.quidcodemo.ui.artists.model.ArtistListOutputModel
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

class AlbumsViewModel @Inject constructor() : ViewModel() {

    private val albumApiResponse = MutableLiveData<APIRequestResponseHandler<GetAlbumOutputModel?>>()

    fun albumApiResult(): MutableLiveData<APIRequestResponseHandler<GetAlbumOutputModel?>> = albumApiResponse

    fun albumApiCall(method:String,username:String,format:String ,page:Int, apiInterface : ApiInterface)
    {
        albumApiResponse.value = APIRequestResponseHandler.loading(null)

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<GetAlbumOutputModel> =
                apiInterface.getUserTopAlbums(method,username, API_KEY,format,page)!!

            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            albumApiResponse.value = APIRequestResponseHandler.success(it)

                            Timber.e(response.body().toString())
                        }
                    } else {
                        albumApiResponse.value = APIRequestResponseHandler.error(null,response.errorBody().toString())
                        Timber.e(response.errorBody().toString())
                    }
                }
            } catch (e: HttpException) {
                Timber.e("Exception ${e.message}")
                albumApiResponse.value = APIRequestResponseHandler.error(null,e.message.toString())
            } catch (e: Throwable) {
                Timber.e("Exception ${e.message}")
                albumApiResponse.value = APIRequestResponseHandler.error(null,e.message.toString())
            }
        }
    }
}