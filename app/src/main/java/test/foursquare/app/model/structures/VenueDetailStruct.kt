package test.foursquare.app.model.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VenueDetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val venue_id:String,
    val rank:String,
    val likes:Int,
    val dislike:Int,
    val rating:Float,
    val ratingColor:String
)