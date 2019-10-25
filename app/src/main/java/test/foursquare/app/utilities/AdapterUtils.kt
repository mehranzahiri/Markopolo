package test.foursquare.app.utilities

import android.graphics.drawable.Drawable
import android.view.animation.AnimationUtils.loadLayoutAnimation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.foursquare.app.R


object AdapterUtils {

    fun initialRecHorizontaly(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        isReverse: Boolean
    ): AdapterUtils {
        recyclerView.layoutManager = LinearLayoutManager(
            recyclerView.context,
            RecyclerView.HORIZONTAL,
            isReverse
        )
        recyclerView.adapter = adapter
        return this

    }

    fun initialRecVertically(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        isReverse: Boolean
    ): AdapterUtils {
        recyclerView.layoutManager = LinearLayoutManager(
            recyclerView.context,
            RecyclerView.VERTICAL,
            isReverse
        )
        recyclerView.adapter = adapter

        return this
    }

    fun addEnterAnimation(recyclerView: RecyclerView): AdapterUtils {
        val resId = R.anim.layout_animation_fall_down
        val animation = loadLayoutAnimation(recyclerView.context, resId)
        recyclerView.layoutAnimation = animation
        return this

    }

    fun addDecorate(recyclerView: RecyclerView, direction: Int, drawable: Drawable?=null): AdapterUtils {
        val mDividerItemDecoration = DividerItemDecoration(recyclerView.context, direction)
        mDividerItemDecoration.setDrawable(drawable!!)
        recyclerView.addItemDecoration(mDividerItemDecoration)
        return this

    }
}