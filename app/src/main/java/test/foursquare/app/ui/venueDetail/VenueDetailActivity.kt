package test.foursquare.app.ui.venueDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R

class VenueDetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: VenueDetailViewModelFactory by instance()
    private lateinit var viewModel: VenueDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_detail)

        viewModel = ViewModelProviders.of(this, factory).get(VenueDetailViewModel::class.java)
    }
}
