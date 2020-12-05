package com.dixitpatel.quidcodemo.ui.tracks.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Streamable {

    @SerializedName("fulltrack")
    @Expose
     val fulltrack: String? = null

    @SerializedName("#text")
    @Expose
     val text: String? = null
}