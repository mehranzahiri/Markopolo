package test.foursquare.app.ui.home

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R
import test.foursquare.app.model.services.IlocationTracker

class MainActivity : AppCompatActivity(), KodeinAware,
    IlocationTracker {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        viewModel.getCurrentLocation().observe(this, Observer { location ->
            Toast.makeText(this, location?.latitude.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    override fun onUserLocationChanged(location: Location?) {

        Toast.makeText(this, "" + location?.longitude, Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.sartTracker()
    }
    override fun onStop() {
        super.onStop()
        viewModel.stopTracker()
    }
}
