package test.foursquare.app.utilities

import android.app.Application
import android.content.Context

class GlobalActivity : Application() {

    companion object {
        private var instance: GlobalActivity? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }
}