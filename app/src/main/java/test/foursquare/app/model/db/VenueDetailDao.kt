package test.foursquare.app.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.foursquare.app.model.structures.VenueDetailStruct

@Dao
interface VenueDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllVenueDetails(venueDetailEntity: List<VenueDetailStruct>)

    @Query("SELECT * FROM venueDetails")
    fun getVenueDetails(): LiveData<List<VenueDetailStruct>>
}