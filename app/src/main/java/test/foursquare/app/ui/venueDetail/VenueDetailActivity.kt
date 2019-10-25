package test.foursquare.app.ui.venueDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R
import test.foursquare.app.utilities.Consts
import test.foursquare.app.utilities.Coroutines
import test.foursquare.app.utilities.GlobalActivity

class VenueDetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: VenueDetailViewModelFactory by instance()
    private lateinit var viewModel: VenueDetailViewModel
    private lateinit var VENUE_ID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_detail)

        VENUE_ID = intent.getStringExtra(Consts.VENUE_ID_KEY)!!
        viewModel = ViewModelProviders.of(this, factory).get(VenueDetailViewModel::class.java)

        Coroutines.main {
            viewModel.getVenueDetail(VENUE_ID).observe(this, Observer { venueDetail ->
                Coroutines.main {

                }

            })
        }
    }
}
