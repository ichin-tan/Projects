package com.example.project_g02

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02.databinding.ActivityLessonDetailsBinding
import com.example.project_g02.models.Lesson
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLessonDetailsBinding
    private lateinit var lesson: Lesson
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SharedPrefResources.FILE_NAME.keyName, Context.MODE_PRIVATE)

        lesson = intent.getSerializableExtra("lesson") as Lesson

        binding.tvLessonName.text = lesson.name
        binding.tvLessonLength.text = lesson.length
        binding.tvLessonDescription.text = lesson.description

        binding.btnWatchLesson.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(lesson.url))
            startActivity(intent)
        }

        binding.btnMarkComplete.setOnClickListener {
            if (lesson.isCompleted) {
                val snackbar = Snackbar.make(binding.root, "Lesson already completed!!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                this.updateDataInSharedPref()
            }
        }
    }

    fun updateDataInSharedPref() {
        with(sharedPreferences.edit()) {
            var strLessonList = sharedPreferences.getString(SharedPrefResources.LESSONS.keyName, "")
            if (!strLessonList.isNullOrEmpty()) {
                val type = object : TypeToken<List<Lesson>>() {}.type // Define type for deserialization\
                val gson = Gson()
                var lessonList: MutableList<Lesson> = gson.fromJson(strLessonList, type)

                for (les in lessonList) {
                    if(les.name.toString() == lesson.name.toString()) {
                        les.isCompleted = true
                        break
                    }
                }
                val strJsonLessons = gson.toJson(lessonList)
                putString(SharedPrefResources.LESSONS.keyName, strJsonLessons)
                apply()

                val snackbar = Snackbar.make(binding.root, "Lesson completed!!", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }
}