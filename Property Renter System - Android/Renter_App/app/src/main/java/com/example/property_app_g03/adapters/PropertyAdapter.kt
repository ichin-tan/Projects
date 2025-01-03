package com.example.property_app_g03.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.property_app_g03.databinding.PropertyRowBinding
import com.example.property_app_g03.interfaces.ClickDetectorInterface
import com.example.property_app_g03.models.Property

class PropertyAdapter(var myItems:List<Property>, val clickInterface: ClickDetectorInterface) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PropertyRowBinding) : RecyclerView.ViewHolder (binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PropertyRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property: Property = myItems.get(position)

        holder.binding.tvRent.text = "Rent:- $${property?.rent.toString()}"
        holder.binding.tvAddress.text = "Address:- ${property?.address}"
        holder.binding.tvBedRoomCount.text = "Bedrooms:- ${property?.bedroomCount.toString()}"

        val imageId = holder.itemView.context.resources.getIdentifier("${property.imgUrl}", "drawable", holder.itemView.context.packageName)
        holder.binding.imgProperty.setImageResource(imageId)

        holder.binding.btnDelete.setOnClickListener {
            clickInterface.deleteRow(position)
        }
    }
}