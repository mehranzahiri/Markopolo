package test.foursquare.app.utilities

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat

object PermissionController {

    fun isLocationEnabled(locationManager: LocationManager): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                GlobalActivity.applicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                GlobalActivity.applicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }
}