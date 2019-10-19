package test.foursquare.app.model.remoteData

import retrofit2.Response
import test.foursquare.app.model.structures.ResponseVenue

class Requests(private val api: ApiInterface) {

    suspend fun fetchVenues(latLng: String, limit: Int, offset: Int): Response<ResponseVenue> {

        return api.exploreVenues(
            latLng, limit, offset
        )

    }
}