package com.dixitpatel.quidcodemo.ui.tracks.model

import com.dixitpatel.quidcodemo.ui.artists.model.Artist
import com.dixitpatel.quidcodemo.ui.artists.model.Attr_
import com.dixitpatel.quidcodemo.ui.artists.model.ImageArtist
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Track {

    @SerializedName("@attr")
    @Expose
    val attr: Attr_? = null

    @SerializedName("duration")
    @Expose
     val duration: String? = null

    @SerializedName("playcount")
    @Expose
     val playcount: String? = null

    @SerializedName("artist")
    @Expose
     val artist: Artist? = null

    @SerializedName("image")
    @Expose
     val image: List<ImageArtist>? = null

    @SerializedName("streamable")
    @Expose
     val streamable: Streamable? = null

    @SerializedName("mbid")
    @Expose
     val mbid: String? = null

    @SerializedName("name")
    @Expose
     val name: String? = null

    @SerializedName("url")
    @Expose
     val url: String? = null
}
