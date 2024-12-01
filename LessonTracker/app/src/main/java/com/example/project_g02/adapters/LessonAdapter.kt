package com.example.project_g02.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g02.databinding.CourseRowBinding
import com.example.project_g02.interfaces.LessonClickedInterface
import com.example.project_g02.models.Lesson

class LessonAdapter(val lessonList:MutableList<Lesson>, val lcInterface:LessonClickedInterface) : RecyclerView.Adapter<LessonAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: CourseRowBinding) : RecyclerView.ViewHolder (binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CourseRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lesson = this.lessonList[position]

        holder.binding.tvLessonName.text = "${lesson.name}"
        holder.binding.tvLessonLength.text = "Length: ${lesson.length}"

        val imageId = holder.itemView.context.resources.getIdentifier("${lesson.imgFileName}", "drawable", holder.itemView.context.packageName)
        holder.binding.imageLesson.setImageResource(imageId)

        Log.d("Adapter Lesson ${lesson.name}","Coming in adapter for lesson: ${lesson.name} which is completed: ${lesson.isCompleted}")
        if(lesson.isCompleted) {
            val imageId = holder.itemView.context.resources.getIdentifier("baseline_check_circle_outline_24", "drawable", holder.itemView.context.packageName)
            holder.binding.imageCheck.setImageResource(imageId)
        } else {
            holder.binding.imageCheck.setImageDrawable(null)
        }

        holder.binding.lessonRow.setOnClickListener {
            lcInterface.lessonClickOn(position)
        }
    }
}