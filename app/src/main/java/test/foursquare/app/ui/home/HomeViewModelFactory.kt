package test.foursquare.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.foursquare.app.model.LocationTrackingRepository
import test.foursquare.app.model.VenueRepository

class HomeViewModelFactory(
    private val venueRepository: VenueRepository,
    private val locationTrackingRepository: LocationTrackingRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(venueRepository, locationTrackingRepository) as T
    }
}