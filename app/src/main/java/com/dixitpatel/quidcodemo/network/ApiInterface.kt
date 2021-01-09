package com.dixitpatel.quidcodemo.network

import com.dixitpatel.quidcodemo.ui.albums.model.GetAlbumOutputModel
import com.dixitpatel.quidcodemo.ui.artists.model.ArtistListOutputModel
import com.dixitpatel.quidcodemo.ui.login.model.LoginOutPutModel
import com.dixitpatel.quidcodemo.ui.tracks.model.GetTrackOutPutModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 *  All Network Calling Apis are define here with Coroutine function.
 */
interface ApiInterface {

    // Get User Top Album
    @GET("2.0")
    suspend fun getUserTopAlbums(
        @Query("method") method: String?,
        @Query("user") username: String?,
        @Query("api_key") api_key: String?,
        @Query("format") format: String?,
        @Query("page") page: Int?
    ): Response<GetAlbumOutputModel>?

    // Get User Top Artists
    @GET("2.0")
    suspend fun getUserTopArtists(
        @Query("method") method: String?,
        @Query("user") username: String?,
        @Query("api_key") api_key: String?,
        @Query("format") format: String?,
        @Query("page") page: Int?
    ): Response<ArtistListOutputModel>?

    // Get User Top Tracks
    @GET("2.0")
    suspend fun getUserTopTracks(
        @Query("method") method: String?,
        @Query("user") username: String?,
        @Query("api_key") api_key: String?,
        @Query("format") format: String?,
        @Query("page") page: Int?
    ): Response<GetTrackOutPutModel>?

    // User LoginAPI
    @POST("2.0")
    suspend fun loginAPI(
        @Query("method") method: String?,
        @Query("username") username: String?,
        @Query("password") password: String?,
        @Query("api_key") apiKey: String?,
        @Query("api_sig") apiSig: String?,
        @Query("format") format: String?
    ): Response<LoginOutPutModel>?

}