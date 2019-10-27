package test.foursquare.app.model.remoteData

import java.io.IOException

class NoConnectivityException : IOException() {

    // You can send any message whatever you want from here.
    override val message: String
        get() = "No Internet Connection"
}