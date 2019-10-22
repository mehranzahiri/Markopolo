package test.foursquare.app.model.services

import android.location.Location

interface IlocationTracker {

    fun onUserLocationChanged(location: Location?)

}