package com.example.project_g02

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_g02.adapters.LessonAdapter
import com.example.project_g02.databinding.ActivityLessonListBinding
import com.example.project_g02.interfaces.LessonClickedInterface
import com.example.project_g02.models.Lesson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonListActivity : AppCompatActivity(), LessonClickedInterface {

    private lateinit var binding: ActivityLessonListBinding
    lateinit var adapter: LessonAdapter
    private lateinit var sharedPreferences: SharedPreferences

    var lessonList: MutableList<Lesson> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SharedPrefResources.FILE_NAME.keyName, Context.MODE_PRIVATE)

        this.lessonList = this.getLessonsFromSharedPref()

        adapter = LessonAdapter(this.lessonList, this)
        binding.rvLessons.adapter = adapter
        binding.rvLessons.layoutManager = LinearLayoutManager(this)
        binding.rvLessons.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onStart() {
        super.onStart()
        val updatedLessonList = this.getLessonsFromSharedPref()
        this.lessonList.clear()
        this.lessonList.addAll(updatedLessonList)
        this.adapter.notifyDataSetChanged()
    }

    fun getLessonsFromSharedPref() : MutableList<Lesson> {
        var strLessonList = sharedPreferences.getString(SharedPrefResources.LESSONS.keyName, "")
        if (!strLessonList.isNullOrEmpty()) {
            val type = object : TypeToken<List<Lesson>>() {}.type // Define type for deserialization\
            val gson = Gson()
            return gson.fromJson(strLessonList, type)
        }
        return mutableListOf()
    }

    override fun lessonClickOn(position: Int) {
        val intent: Intent = Intent(this@LessonListActivity, LessonDetailsActivity::class.java)
        intent.putExtra("lesson",this.lessonList[position])
        startActivity(intent)
    }
}