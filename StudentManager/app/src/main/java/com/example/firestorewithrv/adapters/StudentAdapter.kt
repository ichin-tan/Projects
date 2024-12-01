package com.example.firestorewithrv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firestorewithrv.databinding.RowLayoutBinding
import com.example.firestorewithrv.interfaces.ClickDetectorInterface
import com.example.firestorewithrv.models.Student


class StudentAdapter(val myItems:MutableList<Student>, val clickInterface: ClickDetectorInterface) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder (binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myItems.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myStudent: Student = this.myItems[position]
        holder.binding.tvRow1.text = "Name: ${myStudent.name}"
        holder.binding.tvRow2.text = "GPA: ${myStudent.gpa}"

        val imageId = holder.itemView.context.resources.getIdentifier("baseline_check_circle_outline_24", "drawable", holder.itemView.context.packageName)
        holder.binding.imageView.setImageResource(imageId)

        holder.binding.btnUpdate.setOnClickListener {
            clickInterface.updateRow(position)
        }

        holder.binding.btnDelete.setOnClickListener {
            clickInterface.deleteRow(position)
        }
    }
}