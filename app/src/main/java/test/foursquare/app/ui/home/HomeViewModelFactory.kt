package test.foursquare.app.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.foursquare.app.model.LocationTrackingRepository
import test.foursquare.app.model.VenueRepository
import test.foursquare.app.utilities.GlobalActivity

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