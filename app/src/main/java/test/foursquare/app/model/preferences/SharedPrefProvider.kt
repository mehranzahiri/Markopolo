package test.foursquare.app.model.preferences

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import com.google.gson.Gson
import test.foursquare.app.utilities.Consts
import test.foursquare.app.utilities.GlobalActivity

class SharedPrefProvider {
    private val preference: SharedPreferences
        get() = GlobalActivity.applicationContext().getSharedPreferences(
            Consts.MARKOPOLO_PREF_KEY,
            Context.MODE_PRIVATE
        )

    //    getter setter for detect first arrival
    fun setFirstArrival(isFirstArrival: Boolean) {
        preference.edit().putBoolean(
            Consts.KEY_FIRST_ARRIVAL
            , isFirstArrival
        ).apply()
    }

    fun isFirstArrival(): Boolean =
        preference.getBoolean(Consts.KEY_FIRST_ARRIVAL, false)


    //    getter setter for last location
    fun saveLastLocation(location: Location) {
        preference.edit().putString(
            Consts.LAST_LOCATION_KEY,
            Gson().toJson(location)
        ).apply()
    }

    fun getLastLocation(): Location {
        if (!preference.getString(Consts.LAST_LOCATION_KEY, "").equals("")) {
            return Gson().fromJson(
                preference.getString(Consts.LAST_LOCATION_KEY, ""),
                Location::class.java
            )
        }

        val location = Location("")
        location.latitude = 0.0
        location.longitude = 0.0
        return location
    }
}