package test.foursquare.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.foursquare.app.model.VenueRepository

class HomeViewModelFactory(private val venueRepository: VenueRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(venueRepository) as T
    }
}