package test.foursquare.app.model

import test.foursquare.app.model.services.LocationTracker
import test.foursquare.app.utilities.GlobalActivity

class LocationTrackingRepository(private val locationTracker: LocationTracker) {

    val currentLocation by lazy {
        LocationTracker(GlobalActivity.applicationContext())
    }
}