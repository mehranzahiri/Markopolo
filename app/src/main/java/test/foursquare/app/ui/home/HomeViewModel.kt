package test.foursquare.app.ui.home

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.foursquare.app.model.LocationTrackingRepository
import test.foursquare.app.model.VenueRepository
import test.foursquare.app.utilities.Coroutines

class HomeViewModel(
    private val venueRepository: VenueRepository,
    private val locationTrackingRepository: LocationTrackingRepository
) : ViewModel() {

    fun exploreVenue(latLng: String, limit: Int, offset: Int) {
        Coroutines.main {
            val venueList = venueRepository.exploreVenue(latLng, limit, offset)
        }
    }


    fun getCurrentLocation() = locationTrackingRepository.getCurrentLocation()

    fun sartTracker() {
        locationTrackingRepository.startLocationTracker()
    }

    fun stopTracker() {
        locationTrackingRepository.stopLocationTracker()
    }
}