package test.foursquare.app.model.structures

data class VenueStruct(
    val id: String,
    val name: String,
    val locationStruct: LocationStruct?,
    val categoryStruct: List<CategoryStruct>?
)