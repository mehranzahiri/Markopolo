package test.foursquare.app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_avenue.view.*
import test.foursquare.app.R
import test.foursquare.app.model.structures.VenueStruct

class VenueAdapter(private val context: Context, private val venueList: ArrayList<VenueStruct>) :
    RecyclerView.Adapter<VenueAdapter.HolderStruct>() {

    //    notify item remove by position
    fun removeItem(position: Int) {
        venueList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, venueList.size)
    }

    //     notify item added by position
    fun restoreItem(item: VenueStruct, position: Int) {
        venueList.add(position, item)
        notifyItemInserted(position)
    }

    //     notify multi item added by position
    fun restoreItems(items: ArrayList<VenueStruct>, position: Int) {
        venueList.addAll(position, items)
        notifyItemRangeInserted(position, position + items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderStruct {
        val view = LayoutInflater.from(context).inflate(R.layout.item_avenue, parent, false)
        return HolderStruct(view)
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

    override fun onBindViewHolder(holder: HolderStruct, position: Int) {
        val item = venueList[position]
        holder.bindData(item)
    }

    class HolderStruct(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(venueStruct: VenueStruct) {
            itemView.txt_title.text = venueStruct.name
            itemView.txt_address.text = venueStruct.address
            itemView.txt_title.text = venueStruct.categoryStruct?.get(0)?.shortName
            itemView.txt_distance.text = venueStruct.locationStruct?.distance.toString()
            Glide.with(itemView.context)
                .load(venueStruct.photo)
                .thumbnail(Glide.with(itemView.context).load(R.drawable.gif_placeholder))
                .into(itemView.img_place)
        }
    }
}