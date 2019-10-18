package test.foursquare.app.model.localData

import android.content.Context
import android.content.SharedPreferences
import test.foursquare.app.utilities.GlobalActivity

private const val MARKOPOLO_PREF_KEY = "markopolo_pref_key"
private const val KEY_FIRST_ARRIVAL = "key_first_arrival"

class SharedPrefProvider {
    private val preference: SharedPreferences
        get() = GlobalActivity.applicationContext().getSharedPreferences(
            MARKOPOLO_PREF_KEY,
            Context.MODE_PRIVATE
        )

    fun setFirtArrival(isFirstArrival: Boolean) {
        preference.edit().putBoolean(
            KEY_FIRST_ARRIVAL
            , isFirstArrival
        ).apply()
    }

    fun isFirstArrival():Boolean{
        return preference.getBoolean(KEY_FIRST_ARRIVAL,false)
    }
}