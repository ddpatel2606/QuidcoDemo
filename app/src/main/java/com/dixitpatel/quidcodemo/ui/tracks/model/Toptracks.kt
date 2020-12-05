package com.dixitpatel.quidcodemo.ui.tracks.model

import com.dixitpatel.quidcodemo.ui.artists.model.Attr_
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Toptracks {
    @SerializedName("@attr")
    @Expose
     val attr: Attr_? = null

    @SerializedName("track")
    @Expose
     val track: ArrayList<Track?>? = null
}
