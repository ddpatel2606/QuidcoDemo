package com.dixitpatel.quidcodemo.ui.albums.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAlbumOutputModel {

    @SerializedName("topalbums")
    @Expose
    val topalbums : Topalbums? = null

}