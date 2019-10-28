package test.foursquare.app.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.foursquare.app.R
import test.foursquare.app.model.structures.VenueStruct

class VenueAdapter(private val context: Context, private val list: List<VenueStruct>) :
    RecyclerView.Adapter<VenueAdapter.CustomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomHolder(
            layoutInflater.inflate(
                R.layout.item_avenue,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class CustomHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    }
}