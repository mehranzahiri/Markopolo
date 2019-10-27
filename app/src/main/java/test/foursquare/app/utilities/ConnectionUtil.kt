package test.foursquare.app.utilities

import android.content.Context
import android.net.ConnectivityManager


object ConnectionUtil {

    fun isOnline(context: Context): Boolean {

        val connMgr = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
