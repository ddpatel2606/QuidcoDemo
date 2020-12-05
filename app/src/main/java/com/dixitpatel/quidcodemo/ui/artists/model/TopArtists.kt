package com.dixitpatel.quidcodemo.ui.artists.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class TopArtists {

    @SerializedName("artist")
    @Expose
    val artist: ArrayList<Artist?>? = null

    @SerializedName("@attr")
    @Expose
    val attr: Attr_? = null
}
