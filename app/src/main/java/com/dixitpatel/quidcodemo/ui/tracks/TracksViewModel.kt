package com.dixitpatel.quidcodemo.ui.tracks

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dixitpatel.quidcodemo.constant.API_KEY
import com.dixitpatel.quidcodemo.network.APIRequestResponseHandler
import com.dixitpatel.quidcodemo.network.ApiInterface
import com.dixitpatel.quidcodemo.ui.artists.model.ArtistListOutputModel
import com.dixitpatel.quidcodemo.ui.tracks.model.GetTrackOutPutModel
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

class TracksViewModel @Inject constructor() : ViewModel() {

    private val trackApiResponse = MutableLiveData<APIRequestResponseHandler<GetTrackOutPutModel?>>()

    fun trackApiResult(): MutableLiveData<APIRequestResponseHandler<GetTrackOutPutModel?>> = trackApiResponse

    fun trackApiCall(method:String,username:String,format:String,page:Int , apiInterface : ApiInterface)
    {
        trackApiResponse.value = APIRequestResponseHandler.loading(null)

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<GetTrackOutPutModel> = apiInterface.getUserTopTracks(method,username, API_KEY,format,page)!!


            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            trackApiResponse.value = APIRequestResponseHandler.success(it)

                            Timber.e(response.body().toString())
                        }
                    } else {
                        trackApiResponse.value = APIRequestResponseHandler.error(null,response.errorBody().toString())
                        Timber.e(response.errorBody().toString())
                    }
                }
            } catch (e: HttpException) {
                Timber.e("Exception ${e.message}")
                trackApiResponse.value = APIRequestResponseHandler.error(null,e.message.toString())
            } catch (e: Throwable) {
                Timber.e("Exception ${e.message}")
                trackApiResponse.value = APIRequestResponseHandler.error(null,e.message.toString())
            }
        }
    }
}