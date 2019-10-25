package test.foursquare.app.ui.home

import android.location.Location
import androidx.lifecycle.ViewModel
import test.foursquare.app.model.LocationTrackingRepository
import test.foursquare.app.model.VenueRepository

class HomeViewModel(
    private val venueRepository: VenueRepository,
    private val locationTrackingRepository: LocationTrackingRepository
) : ViewModel() {

    suspend fun fetchRecommendedVenue(latLng: Location, limit: Int, offset: Int) =
        venueRepository.fetchRecommendedVenue(
            latLng,
            limit,
            offset
        )

    fun getVenueRecommendedList() = venueRepository.getVenueRecommendedList()

    fun getCategoryRecommendedList(id: String) = venueRepository.getCategoryRecommendedList(id)

    fun fetchCurrentLocation() = locationTrackingRepository.getCurrentLocation()

    fun startTracker() {
        locationTrackingRepository.startLocationTracker()
    }

    fun stopTracker() {
        locationTrackingRepository.stopLocationTracker()
    }
}