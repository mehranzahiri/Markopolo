package test.foursquare.app.model.structures

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryStruct(
    @PrimaryKey(autoGenerate = false)
    var cat_id: String,
    var cat_name: String,
    var pluralName: String,
    var short_name: String,
    var icon: String
)
