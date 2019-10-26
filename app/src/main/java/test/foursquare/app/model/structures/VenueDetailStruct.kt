package test.foursquare.app.model.structures

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venueDetails")
data class VenueDetailStruct(
    @PrimaryKey(autoGenerate = true)
    val detail_id: Int,
    val venue_id: String,
    val photo: String?,
    val hours: String?,
    val reasons: String,
    val like: String,
    val dislike: String?,
    val rating: Double?,
    val ratingColor: String?,
    @Embedded
    val venueStruct: VenueStruct
)