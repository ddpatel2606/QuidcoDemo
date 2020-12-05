package com.dixitpatel.quidcodemo.ui.artists.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Artist {


    @SerializedName("mbid")
    @Expose
    val mbid: String? = null

    @SerializedName("url")
    @Expose
    val url: String? = null

    @SerializedName("playcount")
    @Expose
    val playcount: String? = null

    @SerializedName("image")
    @Expose
    val image: List<ImageArtist>? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("streamable")
    @Expose
    val streamable: String? = null
}
