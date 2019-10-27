package test.foursquare.app.ui.venueDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.foursquare.app.model.VenueRepository

class VenueDetailViewModelFactory(
    private val venueRepository: VenueRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VenueDetailViewModel(venueRepository) as T
    }
}