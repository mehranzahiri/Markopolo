package test.foursquare.app.utilities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat

object PermissionController {

    fun checkLocationPermission(activity: Activity): Boolean {
        if (ActivityCompat.checkSelfPermission(
                GlobalActivity.applicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                GlobalActivity.applicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Consts.PERMISSIONS_REQUEST_LOCATION
            )
            return false
        }
        return true
    }
}