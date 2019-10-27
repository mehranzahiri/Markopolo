package test.foursquare.app.ui.venueDetail

import androidx.lifecycle.ViewModel
import test.foursquare.app.model.VenueRepository

class VenueDetailViewModel(private val venueRepository: VenueRepository) : ViewModel() {

    suspend fun getVenueDetail(venueId: String) =
        venueRepository.fetchVenueDetail(
            venueId
        )
}