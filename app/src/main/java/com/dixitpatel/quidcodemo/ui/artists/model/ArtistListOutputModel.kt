package com.dixitpatel.quidcodemo.ui.artists.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ArtistListOutputModel
{
    @SerializedName("topartists")
    @Expose
    val topArtists: TopArtists? = null
}