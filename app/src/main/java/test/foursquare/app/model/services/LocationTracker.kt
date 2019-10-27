package test.foursquare.app.model.services

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*

class LocationTracker(context: Context) : LiveData<Location>() {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            value = result?.lastLocation
        }
    }

    init {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mFusedLocationClient?.lastLocation?.addOnSuccessListener { value = it }
        createLocationRequest()
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    override fun onInactive() {
        super.onInactive()
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
    }
}