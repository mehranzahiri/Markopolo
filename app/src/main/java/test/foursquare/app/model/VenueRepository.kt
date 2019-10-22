package test.foursquare.app.model

import androidx.lifecycle.MutableLiveData
import test.foursquare.app.model.db.VenueDatabse
import test.foursquare.app.model.preferences.SharedPrefProvider
import test.foursquare.app.model.remoteData.Requests
import test.foursquare.app.model.remoteData.SafeApiRequest
import test.foursquare.app.model.structures.VenueStruct
import test.foursquare.app.utilities.Coroutines

class VenueRepository(
    private val sharedPrefProvider: SharedPrefProvider,
    private val db: VenueDatabse,
    private val requests: Requests
) : SafeApiRequest() {

    private val venueEntityList = MutableLiveData<List<VenueStruct>>()

    init {
        venueEntityList.observeForever {
            saveVenus(it)
        }
    }

    suspend fun exploreVenue(latLng: String, limit: Int, offset: Int) =
        requests.fetchVenues(latLng, limit, offset)

    private fun saveVenus(venueEntityList: List<VenueStruct>) {
        Coroutines.io {
            db.getVenueDao().saveAllVenues(venueEntityList)
        }
    }
}