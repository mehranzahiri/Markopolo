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
    val id: String,
    val name: String,
    val category_id: String?,
    val photo: String?,
    val lat: Double,
    val lng: Double,
    val address: String,
    val distance: Int,
    @Embedded
    val categoryStruct: CategoryStruct?
)