package test.foursquare.app.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

object DialogCollection {

     fun showAlertForEnableLocation(activity: Activity) {
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Enable Location")
            .setMessage(("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app"))
            .setPositiveButton(
                "Location Settings"
            ) { _, _ ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.run {
                    startActivity(myIntent)
                }
            }
            .setNegativeButton(
                "Cancel"
            ) { _, _ -> }
        dialog.show()
    }

}