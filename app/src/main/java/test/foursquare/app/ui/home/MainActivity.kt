package test.foursquare.app.ui.home

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R
import test.foursquare.app.model.services.GpsStatus
import test.foursquare.app.model.services.PermissionStatus
import test.foursquare.app.ui.adapters.VenueAdapter
import test.foursquare.app.utilities.*

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private val LIMIT_FETCH = 50
    private val adapter: VenueAdapter by lazy {
        VenueAdapter(this)
    }
    private val waitProgressBar: WaitProgressBar by lazy {
        WaitProgressBar.getInstance(this)
    }

    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            when (status) {
                is GpsStatus.Enabled -> {
                    handleGpsAlertDialog(GpsStatus.Enabled())
                }

                is GpsStatus.Disabled -> {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        R.string.gps_status_disabled,
                        Snackbar.LENGTH_LONG
                    ).show()
                }

            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AdapterUtils.initialRecVertically(
            rec_venues,
            adapter,
            false
        ).addDecorate(
            rec_venues,
            RecyclerView.VERTICAL,
            ContextCompat.getDrawable(this, R.drawable.shape_line)
        )
            .addEnterAnimation(rec_venues)



        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        viewModel.fetchCurrentLocation().observe(this, Observer { location ->

            Coroutines.main {
                viewModel.fetchRecommendedVenue(location!!, LIMIT_FETCH, 0)
            }

        })

        viewModel.getVenueRecommendedList().observe(this, Observer { venueList ->
            Coroutines.main {
                waitProgressBar.showLazyLoadProgressview()
                adapter.restoreItems(venueList, 0)
                waitProgressBar.hideLazyLoadProgressview()
            }

        })

        subscribeToGpsListener()
        subscribeToLocationPermissionListener()

    }

    //    check permission and gps status
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Consts.PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    viewModel.startTracker()
                }
            }
        }
    }

    private fun handleGpsAlertDialog(status: GpsStatus = viewModel.gpsStatusLiveData.value as GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                DialogCollection.hideGpsNotEnabledDialog()
                Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.start_tracker,
                    Snackbar.LENGTH_LONG
                ).show()
//                viewModel.startTracker()
            }
            is GpsStatus.Disabled -> DialogCollection.showGpsNotEnabledDialog(this)
        }
    }

    private fun updatePermissionCheckUI(status: PermissionStatus) {
        when (status) {
            is PermissionStatus.Granted -> {

            }

            is PermissionStatus.Denied -> {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.permission_status_denied,
                    Snackbar.LENGTH_LONG
                ).show()
            }

            is PermissionStatus.Blocked -> {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.permission_status_blocked,
                    Snackbar.LENGTH_LONG
                ).show()

            }
        }

    }

    private fun subscribeToGpsListener() = viewModel.gpsStatusLiveData
        .observe(this, gpsObserver)

    private fun subscribeToLocationPermissionListener() =
        viewModel.locationPermissionStatusLiveData.observe(
            this,
            Observer<PermissionStatus> { status ->
                status?.let {
                    updatePermissionCheckUI(status)
                    when (status) {
                        is PermissionStatus.Granted -> handleGpsAlertDialog()
                        is PermissionStatus.Denied -> PermissionController.checkLocationPermission(
                            this
                        )
                        else -> Snackbar.make(
                            findViewById(android.R.id.content),
                            "No action !!!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        )

}
