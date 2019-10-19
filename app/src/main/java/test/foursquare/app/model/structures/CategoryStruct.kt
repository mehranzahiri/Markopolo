package test.foursquare.app.model.structures

import com.google.gson.annotations.SerializedName

data class CategoryStruct(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pluralName")
    val pluralName: String?,
    @SerializedName("shortName")
    val shortName: String?,
    @SerializedName("icon")
    val icon: PhotoStruct?,
    @SerializedName("primary")
    val primary: Boolean?
)