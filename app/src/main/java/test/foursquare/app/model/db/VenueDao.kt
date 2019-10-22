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

    @Query("SELECT * FROM venues")
    fun getVenues(): LiveData<List<VenueStruct>>

}