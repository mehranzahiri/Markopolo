package test.foursquare.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import test.foursquare.app.R
import test.foursquare.app.ui.home.MainActivity

class SplashActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: SplashViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
            , WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        viewModel.setFirstArrivalIfFlase()

        loadGif(R.drawable.gif_splash)
        scaleAnimation(0f, 0f, 1f, 1f, 500, 300)
        lazyStartActivity(5000)
    }


    fun scaleAnimation(
        fromX: Float, fromY: Float, toX: Float, toY: Float
        , delay: Long, duration: Long
    ) {
        val scaleGrow = ScaleAnimation(
            fromX,
            toX,
            fromY,
            toY,
            Animation.RELATIVE_TO_SELF,
            0.5.toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5.toFloat()
        )
        scaleGrow.startOffset = delay
        scaleGrow.duration = duration
        scaleGrow.fillAfter = true
        img_title.startAnimation(scaleGrow)
    }

    fun loadGif(gifId: Int) {
        Glide.with(this)
            .load(gifId)
            .into(img_logo)
    }

    fun lazyStartActivity(delay: Long) {

        Handler().postDelayed(
            {
                //                add noHistory property in manifest for finish activity
                startActivity(Intent(this, MainActivity::class.java))

            }, delay
        )
    }
}
