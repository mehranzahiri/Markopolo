package test.foursquare.app.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.foursquare.app.model.db.entities.VenueDetailEntity

@Dao
interface VenueDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllVenueDetails(venueDetailEntity: List<VenueDetailEntity>)

    @Query("SELECT * FROM VenueDetailEntity")
    fun getVenueDetails(): LiveData<List<VenueDetailEntity>>
}