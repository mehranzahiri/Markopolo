package test.foursquare.app.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R
import test.foursquare.app.ui.adapters.VenueAdapter
import test.foursquare.app.utilities.AdapterUtils
import test.foursquare.app.utilities.Coroutines

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private val LIMIT_FETCH = 50
    private val adapter: VenueAdapter by lazy {
        VenueAdapter(this, ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)



        viewModel.fetchCurrentLocation().observe(this, Observer { location ->

            Coroutines.main {
                viewModel.fetchRecommendedVenue(location!!, LIMIT_FETCH, 0)
            }

        })

        viewModel.getVenueRecommendedList().observe(this, Observer { venueList ->
            Coroutines.main {
                if (rec_venues.adapter == null) {
                    AdapterUtils.initialRecVertically(
                        rec_venues,
                        VenueAdapter(this, ArrayList(venueList)),
                        false
                    )
                        .addDecorate(
                            rec_venues,
                            RecyclerView.VERTICAL,
                            ContextCompat.getDrawable(this, R.drawable.shape_line)
                        )
                        .addEnterAnimation(rec_venues)
                } else
                    adapter.restoreItems(venueList, 0)
            }

        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.startTracker()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopTracker()
    }
}
