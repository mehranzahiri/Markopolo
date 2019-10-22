package test.foursquare.app.utilities

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapsInitializer

class LocationTracker : Service(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocationManager: LocationManager
    private lateinit var mLocationRequest: LocationRequest
    private val UPDATE_INTERVAL: Long by lazy {
        10 * 1000L
    }

    private val FASTEST_INTERVAL: Long by lazy {
        5 * 1000L
    }

    override fun onConnected(p0: Bundle?) {
        if (!PermissionController.checkLocationPermission())
            return


        // Create the location request
        mLocationRequest = initializeLocationRequest(UPDATE_INTERVAL, FASTEST_INTERVAL)

        startLocationUpdates()

    }

    override fun onConnectionSuspended(p0: Int) {
        mGoogleApiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(p0: Location?) {

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(GlobalActivity.applicationContext())

        mGoogleApiClient = initializeGoogleApiClient()
        mLocationManager = initializeLocationManager()

        checkLocation(mLocationManager)

    }

    private fun initializeLocationManager(): LocationManager {
        return this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun initializeGoogleApiClient(): GoogleApiClient {
        return GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
    }

    private fun initializeLocationRequest(
        updateInterval: Long,
        fastestInterval: Long
    ): LocationRequest {
        return LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(updateInterval)
            .setFastestInterval(fastestInterval)
    }

    private fun checkLocation(locationManager: LocationManager) {
//        if (!PermissionController.isLocationEnabled(locationManager))
//            DialogCollection.showAlertForEnableLocation(this)
    }

    private fun startLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient,
            mLocationRequest, this
        )
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        mGoogleApiClient.connect()


        return START_STICKY
    }
}