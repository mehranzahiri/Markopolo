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
import test.foursquare.app.model.localData.SharedPrefProvider
import test.foursquare.app.ui.splash.SplashViewModelFactory

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

        bind() from singleton { SharedPrefProvider() }

        bind() from singleton { GeneralRepository(instance()) }

        bind() from provider { SplashViewModelFactory(instance()) }
    }
}