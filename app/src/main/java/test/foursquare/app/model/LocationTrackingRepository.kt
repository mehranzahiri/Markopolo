package test.foursquare.app.model

import android.content.Intent
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import test.foursquare.app.model.services.IlocationTracker
import test.foursquare.app.model.services.LocationTracker
import test.foursquare.app.utilities.GlobalActivity

class LocationTrackingRepository : IlocationTracker {

    private val currentLocation = MutableLiveData<Location?>()


    override fun onUserLocationChanged(location: Location?) {
        currentLocation.postValue(location)
    }


    fun startLocationTracker() {
        GlobalActivity.applicationContext()
            .startService(Intent(GlobalActivity.applicationContext(), LocationTracker::class.java))
        LocationTracker.setLocationChageListener(this)
    }

    fun stopLocationTracker() {
        GlobalActivity.applicationContext()
            .stopService(Intent(GlobalActivity.applicationContext(), LocationTracker::class.java))
    }

    fun getCurrentLocation() = currentLocation as LiveData<Location?>
}