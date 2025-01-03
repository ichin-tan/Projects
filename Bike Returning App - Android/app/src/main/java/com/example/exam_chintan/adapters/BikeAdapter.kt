package com.example.exam_chintan.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.exam_chintan.databinding.BikeRowBinding
import com.example.exam_chintan.interfaces.ClickDetectorInterface
import com.example.exam_chintan.models.Bike

class BikeAdapter(var myItems:List<Bike>, val clickInterface: ClickDetectorInterface) : RecyclerView.Adapter<BikeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: BikeRowBinding) : RecyclerView.ViewHolder (binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BikeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bike: Bike = myItems.get(position)

        holder.binding.tvBikeName.text = "Name: ${bike.name}"
        holder.binding.tvAddress.text = "Address: ${bike.address}"
        holder.binding.tvLocation.text = "Location: (${bike.lat},${bike.lon})"

        holder.binding.btnReturnBicycle.isVisible = !bike.isReturned

        holder.binding.btnReturnBicycle.setOnClickListener {
            clickInterface.returnCycle(position)
        }
    }
}