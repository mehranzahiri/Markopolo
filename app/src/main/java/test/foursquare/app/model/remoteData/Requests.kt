package test.foursquare.app.model.remoteData

import retrofit2.Response
import test.foursquare.app.model.structures.ResponsVenue

class Requests(private val api: ApiInterface) {

    suspend fun fetchVenues(latLng: String, limit: Int, offset: Int): Response<ResponsVenue> {

        return api.exploreVenues(
            latLng, limit, offset
        )

    }
}