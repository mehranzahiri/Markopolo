package test.foursquare.app.model.remoteData

import okhttp3.ResponseBody
import retrofit2.Response

class Requests(private val api: ApiInterface) {

    suspend fun fetchVenues(latLng: String, limit: Int, offset: Int): Response<ResponseBody> {

        return api.exploreVenues(
            latLng, limit, offset
        )

    }
}