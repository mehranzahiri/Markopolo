package test.foursquare.app.ui.venueDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_venue_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R
import test.foursquare.app.databinding.ActivityVenueDetailBinding
import test.foursquare.app.model.structures.VenueStruct
import test.foursquare.app.utilities.Consts
import test.foursquare.app.utilities.Coroutines
import test.foursquare.app.utilities.WaitProgressBar

class VenueDetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: VenueDetailViewModelFactory by instance()
    private lateinit var viewModel: VenueDetailViewModel
    private lateinit var venueStruct: VenueStruct
    private lateinit var dataBinding: ActivityVenueDetailBinding
    private val waitProgressBar: WaitProgressBar by lazy {
        WaitProgressBar.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_venue_detail)

        waitProgressBar.showProgressLayout()

        venueStruct = Gson().fromJson(
            intent.getStringExtra(Consts.VENUE_SERIALIZE_KEY),
            VenueStruct::class.java
        )
        viewModel = ViewModelProviders.of(this, factory).get(VenueDetailViewModel::class.java)


        Coroutines.main {
            viewModel.getVenueDetail(venueStruct.id).observe(this, Observer { venueDetail ->
                if (venueDetail != null) {
                    venueDetail.venueStruct = venueStruct
                    dataBinding.venueDetailStruct = venueDetail

                    Glide.with(this)
                        .load(venueDetail.photo)
                        .thumbnail(Glide.with(this).load(R.drawable.gif_placeholder))
                        .into(img_venue)
                }
                waitProgressBar.hideProgressLayout()
            })
        }


    }


}
