package test.foursquare.app.model

import android.location.Location
import android.util.Log
import android.widget.Toast
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
import test.foursquare.app.utilities.GlobalActivity

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
            parseVenueListFromResponse(
                requests.fetchVenues(
                    locationToString(latLng),
                    limit,
                    offset
                ).body()
            )

    }

    suspend fun fetchVenueDetail(id: String): LiveData<VenueDetailStruct> {
        return withContext(Dispatchers.IO) {
            parseVenueDetailFromRespons(
                requests.fetchVenueDetail(
                    "venues/$id"
                ).body()
            )
            getVenueDetailRecommendedList(id)
        }


    }


    private fun locationToString(latLng: Location): String {
        return latLng.latitude.toString() + "," + latLng.longitude.toString()
    }

    private fun isFetchNeeded(latLng: Location, radius: Float): Boolean {
        Toast.makeText(GlobalActivity.applicationContext(),(latLng.distanceTo(sharedPrefProvider.getLastLocation()) > radius).toString(),Toast.LENGTH_LONG).show()
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
                        null
                    )
                )

            } catch (e: JSONException) {

            } catch (e2: NullPointerException) {
            }
        }

        categoryList.postValue(categoryTempList)
        venueEntityList.postValue(venueTempList)

    }

    private fun parseVenueDetailFromRespons(response: ResponseBody?) {
        val venueDetailTemp: VenueDetailStruct?
        try {
            val item: JSONObject = JSONObject(response!!.string()).getJSONObject("response")
                .getJSONObject("venue")

            venueDetailTemp =
                VenueDetailStruct(
                    0,
                    item.getString("id"),
                    item.getJSONObject("bestPhoto").getString("suffix"),
                    item.getJSONObject("hours").getString("status"),
                    item.getJSONObject("reasons").getJSONArray("items").getJSONObject(0).getString("summary"),
                    item.getJSONObject("likes").getString("count"),
                    item.getJSONObject("dislikes").getString("count"),
                    item.getDouble("rating"),
                    item.getString("ratingColor")
                )
            Log.i("mmxx", venueDetailTemp.toString())

            venueDetailList.postValue(venueDetailTemp)

        } catch (e: JSONException) {
        } catch (e2: NullPointerException) {
        }

    }
}