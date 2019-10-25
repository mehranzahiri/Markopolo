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

        AdapterUtils.initialRecVertically(rec_venues, adapter, false)
            .addEnterAnimation(rec_venues)
            .addDecorate(
                rec_venues,
                RecyclerView.VERTICAL,
                ContextCompat.getDrawable(this, R.drawable.shape_line)
            )

        viewModel.fetchCurrentLocation().observe(this, Observer { location ->

            Coroutines.main {
                viewModel.fetchCurrentLocation(location!!, LIMIT_FETCH, 0)
            }

        })

        viewModel.getVenueRecommendedList().observe(this, Observer { venueList ->
            adapter.restoreItems(venueList, 0)
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
