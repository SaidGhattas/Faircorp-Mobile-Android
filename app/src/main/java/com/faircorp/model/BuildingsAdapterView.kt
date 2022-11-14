package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R
import com.faircorp.dto.BuildingDto

class BuildingAdapter(val listener: OnBuildingSelectedListener) : RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder>() { // (1)

    inner class BuildingViewHolder(view: View) : RecyclerView.ViewHolder(view) { // (2)
        val name: TextView = view.findViewById(R.id.txt_building_name)
        val address: TextView = view.findViewById(R.id.txt_building_address)
        val city: TextView = view.findViewById(R.id.txt_building_city)
        val zipCode: TextView = view.findViewById(R.id.txt_building_zipCode)
    }
    private val items = mutableListOf<BuildingDto>() // (3)

    fun update(buildings: List<BuildingDto>) {  // (4)
        items.clear()
        items.addAll(buildings)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size // (5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder { // (6)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_buildings_item, parent, false)
        return BuildingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {  // (7)
        val building = items[position]
        holder.apply {
            name.text = building.name
            address.text = building.address
            city.text = building.city
            zipCode.text = building.zipCode.toString()
            itemView.setOnClickListener { listener?.onBuildingSelected(building.id) }
        }
    }
    override fun onViewRecycled(holder: BuildingAdapter.BuildingViewHolder) { // (2)
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }

    }

}