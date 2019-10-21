package test.foursquare.app.model.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category_id: String,
    val name: String,
    val short_name: String,
    val icon: String
)