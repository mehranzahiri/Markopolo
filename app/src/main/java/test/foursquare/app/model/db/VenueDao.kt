package test.foursquare.app.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.foursquare.app.model.structures.VenueStruct

@Dao
interface VenueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllVenues(venueStruct: List<VenueStruct>)

    @Query("SELECT venues.*,categories.* FROM venues LEFT JOIN categories ON venues.category_id=categories.cat_id ORDER BY created_at Desc")
    fun joinVenuesOnCategory(): LiveData<List<VenueStruct>>

}