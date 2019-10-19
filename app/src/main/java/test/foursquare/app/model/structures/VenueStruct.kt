package test.foursquare.app.model.structures

import com.google.gson.annotations.SerializedName

data class VenueStruct(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val locationStruct: LocationStruct?,
    @SerializedName("categories")
    val categoryStruct: List<CategoryStruct>?
)