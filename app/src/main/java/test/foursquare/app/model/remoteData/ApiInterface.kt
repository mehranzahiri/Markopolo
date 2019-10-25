package test.foursquare.app.model.remoteData

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import test.foursquare.app.utilities.Consts


interface ApiInterface {
    companion object {
        operator fun invoke(apiClient: ApiClient): ApiInterface {
            return apiClient.getClinet()
        }
    }

    @GET(Consts.VENUE_EXPLORE_ROUTE)
    suspend fun exploreVenues(
        @Query("ll") latLng: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<ResponseBody>
}