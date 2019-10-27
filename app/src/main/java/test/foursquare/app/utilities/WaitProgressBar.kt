package test.foursquare.app.utilities

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import test.foursquare.app.R

class WaitProgressBar private constructor(private var activity: Activity) {

    private lateinit var lazy_load_progressview: ProgressBar
    private lateinit var progressLayout: LinearLayout
    private lateinit var img_loading: ImageView

    companion object {
        @Volatile
        private var instance: WaitProgressBar? = null
        fun getInstance(activity: Activity) =
            instance ?: synchronized(this) {
                instance ?: WaitProgressBar(activity).also { instance = it }
            }
    }

    fun showProgressLayout() {

        progressLayout = activity.findViewById(R.id.layout_loading)
        img_loading = activity.findViewById(R.id.img_loading)
        progressLayout.visibility = View.VISIBLE

        Glide.with(activity)
            .load(R.drawable.gif_placeholder)
            .into(img_loading)
    }

    fun showLazyLoadProgressview() {

        lazy_load_progressview = activity.findViewById(R.id.lazy_load_progressview)
        lazy_load_progressview.visibility = View.VISIBLE
    }

    fun hideProgressLayout() {

        progressLayout.visibility = View.GONE
    }


    fun hideLazyLoadProgressview() {

        if (lazy_load_progressview.visibility == View.VISIBLE) {
            lazy_load_progressview.visibility = View.GONE
        }


    }

}