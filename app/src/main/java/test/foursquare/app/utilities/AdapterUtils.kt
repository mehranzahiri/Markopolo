package test.foursquare.app.utilities

import android.view.animation.AnimationUtils.loadLayoutAnimation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.foursquare.app.R


object AdapterUtils {

    fun initialRecHorizontaly(recyclerView: RecyclerView, isReverse: Boolean) {
        recyclerView.layoutManager = LinearLayoutManager(
            recyclerView.context,
            RecyclerView.HORIZONTAL,
            isReverse
        )
    }

    fun initialRecVertically(recyclerView: RecyclerView, isReverse: Boolean) {
        recyclerView.layoutManager = LinearLayoutManager(
            recyclerView.context,
            RecyclerView.VERTICAL,
            isReverse
        )
    }

    fun addEnterAnimation(recyclerView: RecyclerView) {
        val resId = R.anim.layout_animation_fall_down
        val animation = loadLayoutAnimation(recyclerView.context, resId)
        recyclerView.layoutAnimation = animation
    }

    fun addDecorate(recyclerView: RecyclerView, direction: Int) {
        val mDividerItemDecoration = DividerItemDecoration(recyclerView.context, direction)
        recyclerView.addItemDecoration(mDividerItemDecoration)
    }
}