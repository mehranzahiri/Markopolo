package test.foursquare.app.ui.home

import android.Manifest
import android.location.Location
import androidx.lifecycle.ViewModel
import test.foursquare.app.model.LocationTrackingRepository
import test.foursquare.app.model.VenueRepository
import test.foursquare.app.model.services.GpsStatusListener
import test.foursquare.app.model.services.PermissionStatusListener
import test.foursquare.app.utilities.GlobalActivity

class HomeViewModel(
    private val venueRepository: VenueRepository,
    private val locationTrackingRepository: LocationTrackingRepository
) : ViewModel() {

    val gpsStatusLiveData = GpsStatusListener(GlobalActivity.applicationContext())

    val locationPermissionStatusLiveData = PermissionStatusListener(
        GlobalActivity.applicationContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    suspend fun fetchRecommendedVenue(latLng: Location, limit: Int, offset: Int) =
        venueRepository.fetchRecommendedVenue(
            latLng,
            limit,
            offset
        )

    fun getVenueRecommendedList() = venueRepository.getVenueRecommendedList()

    fun getCategoryRecommendedList(id: String) = venueRepository.getCategoryRecommendedList(id)

    fun fetchCurrentLocation() = locationTrackingRepository.currentLocation
}