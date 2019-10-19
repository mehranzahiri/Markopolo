package test.foursquare.app.model.structures

import com.google.gson.annotations.SerializedName

data class ResponsVenue(
    @SerializedName("response")
    val venueDataStruct: VenueDataStruct
) {

    data class VenueDataStruct(
        @SerializedName("groups")
        val venueGroupStruct: List<VenueGroupStruct>?
    )

    data class VenueGroupStruct(
        @SerializedName("items")
        val venueList: List<VenueStruct>
    )
}