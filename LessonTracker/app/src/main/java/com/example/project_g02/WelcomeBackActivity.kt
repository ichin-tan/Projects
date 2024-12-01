package com.example.project_g02

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02.databinding.ActivityWelcomeBackBinding
import com.example.project_g02.models.Lesson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WelcomeBackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBackBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SharedPrefResources.FILE_NAME.keyName, Context.MODE_PRIVATE)
        val currentUserName = sharedPreferences.getString(SharedPrefResources.CURRENT_USER_NAME.keyName, "")

        binding.tvWelcomeBack.text = "Welcome back, ${currentUserName}"
        this.setLessonData()

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this@WelcomeBackActivity, LessonListActivity::class.java)
            startActivity(intent)
        }

        binding.btnDeleteAllDataReset.setOnClickListener {
            with(sharedPreferences.edit()) {
                clear()
                apply()
            }
            val intent = Intent(this@WelcomeBackActivity, EnterNameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        this.setLessonData()
    }

    fun setLessonData() {
        var strLessonList = sharedPreferences.getString(SharedPrefResources.LESSONS.keyName, "")
        if (!strLessonList.isNullOrEmpty()) {
            val type = object : TypeToken<List<Lesson>>() {}.type // Define type for deserialization\
            val gson = Gson()
            var lessons: MutableList<Lesson> = gson.fromJson(strLessonList, type)

            var completedCount = 0
            for(lesson in lessons) {
                if(lesson.isCompleted) {
                    completedCount++
                }
            }
            var remainingCount = lessons.count() - completedCount

            var completedPercentage = (100 * completedCount) / lessons.count()

            binding.tvCompleted.text = "You've completed ${completedPercentage}% of the course"
            binding.tvLessonCompleted.text = "Lessons completed : ${completedCount}"
            binding.tvLessonRemaining.text = "Lessons remaining : ${remainingCount}"
        }
    }
}