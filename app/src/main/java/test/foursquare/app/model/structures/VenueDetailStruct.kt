package test.foursquare.app.model.structures

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venueDetails")
data class VenueDetailStruct(
    @PrimaryKey(autoGenerate = false)
    var venue_id: String,
    var photo: String?,
    var hours: String?,
    var reasons: String?,
    var like: String?,
    var dislike: String?,
    var rating: Double,
    var ratingColor: String,
    @Embedded
    var venueStruct: VenueStruct?
)