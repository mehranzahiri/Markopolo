package test.foursquare.app.model

import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import test.foursquare.app.model.db.VenueDatabse
import test.foursquare.app.model.preferences.SharedPrefProvider
import test.foursquare.app.model.remoteData.Requests
import test.foursquare.app.model.remoteData.SafeApiRequest
import test.foursquare.app.model.structures.CategoryStruct
import test.foursquare.app.model.structures.VenueStruct
import test.foursquare.app.utilities.Consts
import test.foursquare.app.utilities.Coroutines

class VenueRepository(
    private val sharedPrefProvider: SharedPrefProvider,
    private val db: VenueDatabse,
    private val requests: Requests
) : SafeApiRequest() {

    private val venueEntityList = MutableLiveData<ArrayList<VenueStruct>>()
    private val categoryList = MutableLiveData<ArrayList<CategoryStruct>>()

    init {

        categoryList.observeForever {
            saveCategory(it)
        }
        venueEntityList.observeForever {
            saveVenus(it)
        }


    }

    //    webservice
    suspend fun fetchRecommendedVenue(latLng: String, limit: Int, offset: Int) {

        if (isFetchNeeded())
            parseVenueListFromResponse(requests.fetchVenues(latLng, limit, offset).body())

    }


    private fun isFetchNeeded(): Boolean {
        return true
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

    //    getter

    fun getVenueRecommendedList() =
        db.getVenueDao().joinVenuesOnCategory()

    fun getCategoryRecommendedList(id: String) =
        db.getCategoryDao().getCategoryById(id)

    //      parser
    private fun parseVenueListFromResponse(response: ResponseBody?) {
        val venueTempList: ArrayList<VenueStruct> = ArrayList()
        val categoryTempList: ArrayList<CategoryStruct> = ArrayList()

        val items: JSONArray = JSONObject(response!!.string()).getJSONObject("response")
            .getJSONArray("groups").getJSONObject(0).getJSONArray("items")

        for (i in 0 until items.length()) {
            val venueItem: JSONObject = items.getJSONObject(i).getJSONObject("venue")
            val categoryItem: JSONObject = venueItem.getJSONArray("categories").getJSONObject(0)

            try {

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
                        "",
                        venueItem.getJSONObject("location").getDouble("lat"),
                        venueItem.getJSONObject("location").getDouble("lng"),
                        venueItem.getJSONObject("location").getString("address"),
                        venueItem.getJSONObject("location").getInt("distance"),
                        null
                    )
                )


            } catch (e: JSONException) {

            }
        }

        categoryList.postValue(categoryTempList)
        venueEntityList.postValue(venueTempList)
    }
}