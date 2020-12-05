package com.dixitpatel.quidcodemo.ui.albums.model

import com.dixitpatel.quidcodemo.ui.artists.model.Artist
import com.dixitpatel.quidcodemo.ui.artists.model.ImageArtist
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Album {

    @SerializedName("artist")
    @Expose
    val artist: Artist? = null

    @SerializedName("image")
    @Expose
    val image: List<ImageArtist>? = null

    @SerializedName("playcount")
    @Expose
    val playcount: String? = null

    @SerializedName("url")
    @Expose
     val url: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("mbid")
    @Expose
    val mbid: String? = null
}
