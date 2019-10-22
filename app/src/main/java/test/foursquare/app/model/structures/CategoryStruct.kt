package test.foursquare.app.model.structures

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "categories")
data class CategoryStruct(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("id")
    var category_id: String,
    @SerializedName("name")
    var name: String,
    var short_name: String,
    @SerializedName("pluralName")
    var pluralName: String?,
    @SerializedName("shortName")
    var shortName: String?,
    @SerializedName("primary")
    var primary: Boolean?,
    var icon: String?,
    @Ignore
    @SerializedName("icon")
    var photoStruct: PhotoStruct?
){
    constructor() : this(0,"","","","","",false,"",null)
}
