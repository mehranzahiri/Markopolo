package test.foursquare.app.model.structures

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "venues", foreignKeys = [
        ForeignKey(
            entity = CategoryStruct::class,
            parentColumns = ["cat_id"],
            childColumns = ["category_id"],
            onDelete = CASCADE
        )]
)
data class VenueStruct(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var name: String,
    var category_id: String,
    var lat: Double,
    var lng: Double,
    var address: String,
    var distance: Int,
    @Embedded
    var categoryStruct: CategoryStruct?
)