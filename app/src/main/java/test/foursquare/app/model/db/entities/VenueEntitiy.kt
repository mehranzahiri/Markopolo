package test.foursquare.app.model.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VenueEntitiy (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val venue_id:String,
    val venue_detail_id:String,
    val name:String,
    val category_id:String,
    val photo:String,
    val ll:String,
    val address:String
)