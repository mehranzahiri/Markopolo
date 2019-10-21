package test.foursquare.app.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.foursquare.app.model.db.entities.VenueEntitiy

@Dao
interface VenueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllVenues(venueEntity: List<VenueEntitiy>)

    @Query("SELECT * FROM VenueEntitiy")
    fun getVenues(): LiveData<List<VenueEntitiy>>

}