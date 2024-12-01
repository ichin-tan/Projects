package com.example.project_g02

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g02.databinding.ActivityEnterNameBinding
import com.example.project_g02.models.Lesson
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

enum class SharedPrefResources(var keyName:String) {
    FILE_NAME("com.example.project_g02.PREFERENCES"),
    CURRENT_USER_NAME("CURRENT_USER_NAME"),
    IS_USER_FIRST_TIME_ENTER("IS_USER_FIRST_TIME_ENTER"),
    LESSONS("LESSONS")
}

class EnterNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterNameBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(SharedPrefResources.FILE_NAME.keyName, Context.MODE_PRIVATE)
        val isFirstTimeUser = sharedPreferences.getBoolean(SharedPrefResources.IS_USER_FIRST_TIME_ENTER.keyName, true)

        if(isFirstTimeUser === false) {
            val intent = Intent(this, WelcomeBackActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding.btnContinue.setOnClickListener {
            if(this.isVlidated()) {
                with(sharedPreferences.edit()) {
                    putString(SharedPrefResources.CURRENT_USER_NAME.keyName, binding.etEnterName.text.toString())
                    putBoolean(SharedPrefResources.IS_USER_FIRST_TIME_ENTER.keyName, false)
                    val gson = Gson()
                    var lessonList:MutableList<Lesson> = mutableListOf(
                        Lesson("Variables", "9 min", "variables","In this tutorial we will learn about Variables in Kotlin","https://www.youtube.com/watch?v=roEVP47TsDU"),
                        Lesson("Functions", "15 min","functions","In this tutorial we will learn about Functions in Kotlin","https://www.youtube.com/watch?v=obN78NEd47g"),
                        Lesson("Loops", "22 min","loops","In this tutorial we will learn about Loops in Kotlin","https://www.youtube.com/watch?v=8WaV7Nq7eGw"),
                        Lesson("Classes & Objects", "8 min","classes","In this tutorial we will learn about Classes and Objects in Kotlin","https://www.youtube.com/watch?v=Fsgv45Q5W20"),
                        Lesson("Inheritance", "7 min","inheritance","In this tutorial we will learn about Inheritance in Kotlin","https://www.youtube.com/watch?v=33DNMPOFvuA")
                    )
                    val strJsonLessons = gson.toJson(lessonList)
                    putString(SharedPrefResources.LESSONS.keyName, strJsonLessons)
                    apply()
                }
                val intent: Intent = Intent(this@EnterNameActivity, WelcomeBackActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun isVlidated(): Boolean {
        if(binding.etEnterName.text.toString().isEmpty()) {
            val snackbar = Snackbar.make(binding.root, "ERROR: Please enter name!!!", Snackbar.LENGTH_LONG)
            snackbar.show()
            return false
        }
        return true
    }
}