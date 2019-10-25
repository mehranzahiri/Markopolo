package test.foursquare.app.model.structures

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryStruct(
    @PrimaryKey(autoGenerate = false)
    val cat_id: String,
    val cat_name: String,
    val pluralName: String,
    val short_name: String,
    val icon: String
)
