package test.foursquare.app.ui.home

import androidx.lifecycle.ViewModel
import test.foursquare.app.model.VenueRepository
import test.foursquare.app.utilities.Coroutines

class HomeViewModel(private var venueRepository: VenueRepository) : ViewModel() {

    fun exploreVenue(latLng: String, limit: Int, offset: Int) {
        Coroutines.main {
            val venueList = venueRepository.exploreVenue(latLng, limit, offset)
        }
    }
}