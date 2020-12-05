package com.dixitpatel.quidcodemo.ui.albums.model

import com.dixitpatel.quidcodemo.ui.artists.model.Attr_

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Topalbums {

    @SerializedName("album")
    @Expose
    val album: ArrayList<Album?>? = null

    @SerializedName("@attr")
    @Expose
    val attr: Attr_? = null

}
