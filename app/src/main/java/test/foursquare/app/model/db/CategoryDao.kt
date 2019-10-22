package test.foursquare.app.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.foursquare.app.model.structures.CategoryStruct

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCategories(categoryEntity: List<CategoryStruct>)

    @Query("SELECT * FROM categories")
    fun getCategories(): LiveData<List<CategoryStruct>>
}