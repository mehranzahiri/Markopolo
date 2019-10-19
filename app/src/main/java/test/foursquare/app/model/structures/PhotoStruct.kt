package test.foursquare.app.model.structures

import com.google.gson.annotations.SerializedName

data class PhotoStruct(
    @SerializedName("prefix")
    val prefix: String?,
    @SerializedName("suffix")
    val suffix: String?
    )