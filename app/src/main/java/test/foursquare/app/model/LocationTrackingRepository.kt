package test.foursquare.app.model

import test.foursquare.app.model.services.LocationTracker

class LocationTrackingRepository(private val locationTracker: LocationTracker) {

    val currentLocation by lazy {
        locationTracker
    }
}