package test.foursquare.app.utilities

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import test.foursquare.app.R

object DialogCollection {
    private var alertDialog: AlertDialog? = null

    fun showGpsNotEnabledDialog(activity: Activity) {
        if (alertDialog?.isShowing == true) {
            return // null or already being shown
        }

        alertDialog = AlertDialog.Builder(activity)
            .setTitle(R.string.gps_required_title)
            .setMessage(R.string.gps_required_body)
            .setPositiveButton(R.string.action_settings) { _, _ ->
                // Open app's settings.
                val intent = Intent().apply {
                    action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                }
                activity.startActivity(intent)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    fun hideGpsNotEnabledDialog() {
        if (alertDialog?.isShowing == true) alertDialog?.dismiss()
    }
}