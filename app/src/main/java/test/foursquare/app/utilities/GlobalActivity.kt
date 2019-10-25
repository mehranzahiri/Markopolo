package test.foursquare.app.utilities

import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import test.foursquare.app.model.GeneralRepository
import test.foursquare.app.model.LocationTrackingRepository
import test.foursquare.app.model.VenueRepository
import test.foursquare.app.model.db.VenueDatabse
import test.foursquare.app.model.preferences.SharedPrefProvider
import test.foursquare.app.model.remoteData.ApiClient
import test.foursquare.app.model.remoteData.ApiInterface
import test.foursquare.app.model.remoteData.Requests
import test.foursquare.app.ui.home.HomeViewModelFactory
import test.foursquare.app.ui.splash.SplashViewModelFactory
import test.foursquare.app.ui.venueDetail.VenueDetailViewModelFactory

class GlobalActivity : Application(), KodeinAware {

    companion object {
        private var instance: GlobalActivity? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GlobalActivity))

//        utils
        bind() from singleton { SharedPrefProvider() }
        bind() from singleton { VenueDatabse(instance()) }

//        repositories
        bind() from singleton { GeneralRepository(instance()) }
        bind() from singleton { VenueRepository(instance(), instance(), instance()) }
        bind() from singleton { LocationTrackingRepository() }

//        factories
        bind() from provider { SplashViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance(), instance()) }
        bind() from provider { VenueDetailViewModelFactory(instance()) }

//        webservice
        bind() from singleton { ApiClient() }
        bind() from singleton { ApiInterface(instance()) }
        bind() from singleton { Requests(instance()) }

    }
}