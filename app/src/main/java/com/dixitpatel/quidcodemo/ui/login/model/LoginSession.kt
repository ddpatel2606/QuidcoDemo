package com.dixitpatel.quidcodemo.ui.login.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class LoginSession {

    @SerializedName("subscriber")
    @Expose
    val subscriber: Int? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("key")
    @Expose
    val key: String? = null
}