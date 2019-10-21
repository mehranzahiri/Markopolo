package test.foursquare.app.model

import test.foursquare.app.model.preferences.SharedPrefProvider
import test.foursquare.app.model.remoteData.Requests

class VenueRepository(private val sharedPrefProvider: SharedPrefProvider,
    private val requests: Requests)
{

    suspend fun exploreVenue(latLng: String, limit: Int, offset: Int) =
        requests.fetchVenues(latLng, limit, offset)

}