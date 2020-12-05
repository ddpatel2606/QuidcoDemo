package com.dixitpatel.quidcodemo.ui.artists.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Attr_ {

    @SerializedName("page")
    @Expose
     val page: String? = null

    @SerializedName("total")
    @Expose
     val total: String? = null

    @SerializedName("user")
    @Expose
     val user: String? = null

    @SerializedName("perPage")
    @Expose
     val perPage: String? = null

    @SerializedName("totalPages")
    @Expose
     val totalPages: String? = null
}
