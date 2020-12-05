package com.dixitpatel.quidcodemo.ui.login.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class LoginOutPutModel
{
    @SerializedName("session")
    @Expose
    var session: LoginSession? = null

}