package test.foursquare.app.model.remoteData

import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query
import test.foursquare.app.model.structures.ResponsVenue
import test.foursquare.app.utilities.Consts


interface ApiInterface {
    companion object{
        operator fun invoke(apiClient: ApiClient):ApiInterface{
            return apiClient.getClinet()
        }
    }

    @FormUrlEncoded
    @GET(Consts.VENUE_EXPLORE_ROUTE)
    suspend fun exploreVenues(
        @Query("ll") latLng: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<ResponsVenue>
}