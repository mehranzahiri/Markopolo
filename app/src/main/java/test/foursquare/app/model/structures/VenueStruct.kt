package test.foursquare.app.model.structures

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "venues")
data class VenueStruct(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var venue_detail_id: String,
    @SerializedName("id")
    var venue_id: String,
    @SerializedName("name")
    var name: String,
    @Ignore
    @SerializedName("location")
    val locationStruct: LocationStruct?,
    @Ignore
    @SerializedName("categories")
    val categoryStruct: List<CategoryStruct>?,
    var category_id: String,
    var photo: String,
    var ll: String,
    var address: String
){
    constructor() : this(1,"","","",null,null,"","","","")
}