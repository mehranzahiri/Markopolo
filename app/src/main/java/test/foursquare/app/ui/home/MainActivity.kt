package test.foursquare.app.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R

class MainActivity : AppCompatActivity(), KodeinAware {


    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        viewModel.exploreVenue(
            "48.8588377,2.2770195"
            , 5, 1
        )
    }
}
