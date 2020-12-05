package com.dixitpatel.quidcodemo.ui.artists.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ImageArtist {

    @SerializedName("size")
    @Expose
    val size: String? = null

    @SerializedName("#text")
    @Expose
    val text: String? = null
}
