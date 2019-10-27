package test.foursquare.app.model

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import test.foursquare.app.model.db.VenueDatabse
import test.foursquare.app.model.preferences.SharedPrefProvider
import test.foursquare.app.model.remoteData.Requests
import test.foursquare.app.model.remoteData.SafeApiRequest
import test.foursquare.app.model.structures.CategoryStruct
import test.foursquare.app.model.structures.VenueDetailStruct
import test.foursquare.app.model.structures.VenueStruct
import test.foursquare.app.utilities.Consts
import test.foursquare.app.utilities.Coroutines
import java.io.IOException

class VenueRepository(
    private val sharedPrefProvider: SharedPrefProvider,
    private val db: VenueDatabse,
    private val requests: Requests
) : SafeApiRequest() {

    private val venueEntityList = MutableLiveData<ArrayList<VenueStruct>>()
    private val categoryList = MutableLiveData<ArrayList<CategoryStruct>>()
    private val venueDetailList = MutableLiveData<VenueDetailStruct>()

    init {

        categoryList.observeForever {
            saveCategory(it)
        }
        venueEntityList.observeForever {
            saveVenus(it)
        }

        venueDetailList.observeForever {
            saveVenusDetail(it)
        }


    }

    //    webservice
    suspend fun fetchRecommendedVenue(latLng: Location, limit: Int, offset: Int) {

        if (isFetchNeeded(latLng, Consts.VARIANCE_VALUE))
            try {
                parseVenueListFromResponse(
                    requests.fetchVenues(
                        locationToString(latLng),
                        limit,
                        offset
                    ).body()
                )
            } catch (e: IOException) {
            }


    }

    suspend fun fetchVenueDetail(id: String): LiveData<VenueDetailStruct>? {
        return withContext(Dispatchers.IO) {
            try {
                parseVenueDetailFromResponse(
                    requests.fetchVenueDetail(
                        "venues/$id"
                    ).body()
                )
            } catch (e: IOException) {
            }

            getVenueDetailRecommendedList(id)
        }


    }


    private fun locationToString(latLng: Location): String {
        return latLng.latitude.toString() + "," + latLng.longitude.toString()
    }

    private fun isFetchNeeded(latLng: Location, radius: Float): Boolean {

        if (latLng.distanceTo(sharedPrefProvider.getLastLocation()) > radius) {
            saveLastLocation(latLng)
            return true
        }

        return false
    }

    //    preferences
    private fun saveLastLocation(latLng: Location) {
        Coroutines.io {
            sharedPrefProvider.saveLastLocation(latLng)
        }
    }

    //    db
    private fun saveCategory(categoryList: List<CategoryStruct>) {
        Coroutines.io {
            db.getCategoryDao().saveAllCategories(categoryList)
        }
    }

    private fun saveVenus(venueEntityList: ArrayList<VenueStruct>) {
        Coroutines.io {
            db.getVenueDao().saveAllVenues(venueEntityList)
        }
    }

    private fun saveVenusDetail(venueDetail: VenueDetailStruct) {
        Coroutines.io {
            db.getVenueDetailDao().saveVenueDetails(venueDetail)
        }
    }

    //    getter

    fun getVenueRecommendedList() =
        db.getVenueDao().joinVenuesOnCategory()

    fun getCategoryRecommendedList(id: String) =
        db.getCategoryDao().getCategoryById(id)

    fun getVenueDetailRecommendedList(id: String) =
        db.getVenueDetailDao().getVenueDetail(id)


    /**    parser venue list **/
    private fun parseVenueListFromResponse(response: ResponseBody?) {
        val venueTempList: ArrayList<VenueStruct> = ArrayList()
        val categoryTempList: ArrayList<CategoryStruct> = ArrayList()


        val items: JSONArray = JSONObject(response!!.string()).getJSONObject("response")
            .getJSONArray("groups").getJSONObject(0).getJSONArray("items")

        for (i in 0 until items.length()) {
            try {
                val venueItem: JSONObject = items.getJSONObject(i).getJSONObject("venue")
                val categoryItem: JSONObject = venueItem.getJSONArray("categories").getJSONObject(0)


                categoryTempList.add(
                    CategoryStruct(
                        categoryItem.getString("id"),
                        categoryItem.getString("name"),
                        categoryItem.getString("pluralName"),
                        categoryItem.getString("shortName"),
                        categoryItem.getJSONObject("icon").getString("prefix") +
                                Consts.CATEGORY_ICON_SIZE +
                                categoryItem.getJSONObject("icon").getString("suffix")

                    )
                )

                venueTempList.add(
                    VenueStruct(
                        venueItem.getString("id"),
                        venueItem.getString("name"),
                        categoryItem.getString("id"),
                        venueItem.getJSONObject("location").getDouble("lat"),
                        venueItem.getJSONObject("location").getDouble("lng"),
                        venueItem.getJSONObject("location").getString("address"),
                        venueItem.getJSONObject("location").getInt("distance"),
                        null,
                        System.currentTimeMillis()
                    )
                )

            } catch (e: JSONException) {

            } catch (e2: NullPointerException) {
            }
        }

        categoryList.postValue(categoryTempList)
        venueEntityList.postValue(venueTempList)

    }

    private fun parseVenueDetailFromResponse(response: ResponseBody?) {
        val venueDetailTemp: VenueDetailStruct?
        try {
            val item: JSONObject = JSONObject(response!!.string()).getJSONObject("response")
                .getJSONObject("venue")

            var hours = "No schedule for this Place."
            var summary = "No summary"
            var likes = "0"
            var dislikes = "0"

            if (item.has("hours") && item.getJSONObject("hours").get("status") != "")
                hours = item.getJSONObject("hours").getString("status")

            if (item.getJSONObject("reasons").has("items") && item.getJSONObject("reasons").getJSONArray(
                    "items"
                ).length() > 0
            )
                summary = item.getJSONObject("reasons").getJSONArray("items").getJSONObject(0)
                    .getString("summary")

            if (item.has("likes") && item.get("likes") is JSONObject)
                likes = item.getJSONObject("likes").getString("count")

            if (item.has("dislikes") && item.get("dislikes") is JSONObject)
                dislikes = item.getJSONObject("dislikes").getString("count")

            venueDetailTemp =
                VenueDetailStruct(
                    item.getString("id"),
                    item.getJSONObject("bestPhoto").getString("prefix")
                        .plus(
                            Consts.PHOTO_DETAIL_SIZE_VALUE
                        )
                        .plus(
                            item.getJSONObject("bestPhoto").getString("suffix")
                        ),
                    hours,
                    summary,
                    likes,
                    dislikes,
                    item.getDouble("rating"),
                    item.getString("ratingColor"),
                    null
                )

            venueDetailList.postValue(venueDetailTemp)

        } catch (e: JSONException) {
        } catch (e2: NullPointerException) {
        }

    }
}