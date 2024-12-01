package com.example.firestorewithrv

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.firestorewithrv.databinding.ActivityEditScreenBinding
import com.example.firestorewithrv.models.Student
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditScreen : AppCompatActivity() {

    lateinit var binding: ActivityEditScreenBinding
    var isEdit = false
    var stuID = ""

    // TODO: Add a reference to the database
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.isEdit = intent.getBooleanExtra("is_edit", false)

        if (isEdit) {
            this.stuID = intent.getStringExtra("student_id") as String
            this.showStudentToUpdateData(stuID)
        } else {
            this.binding.tvTitle.text = "Add Student"
            this.binding.etDocId.isVisible = false
            this.binding.btnSubmit.setText("Add Student")
            binding.etName.setText("")
            binding.etGpa.setText("")
            binding.swHasCar.isChecked = false

        }

        // click handler
        binding.btnSubmit.setOnClickListener {
            if (isEdit) {
                this.updateStudent()
            } else {
                this.addStudent()
            }
            finish()
        }
    }

    fun showStudentToUpdateData(stuID: String) {
        db.collection("students").document(stuID).get().addOnSuccessListener { document: DocumentSnapshot ->

            val student:Student? = document.toObject(Student::class.java)


            // if document with specified id CANNOT be found,
            // then student == null
            if (student == null) {
                Log.d("TESTING", "No results found")
                return@addOnSuccessListener
            }

            // if you reach this point, then we found a student
            Log.d("TESTING", student.toString())

            // update hte ui with the student info
            binding.etDocId.setText(student.id)
            binding.etName.setText(student.name)
            binding.etGpa.setText(student.gpa.toString())
            binding.swHasCar.isChecked = student.hasCar

        }.addOnFailureListener {
                exception ->
            Log.w("TESTING", "Error getting documents.", exception)
        }
    }

    fun updateStudent() {
        // get values from form
        val nameFromUI:String = binding.etName.text.toString()
        val gpaFromUI:Double = binding.etGpa.text.toString().toDouble()
        val hasCarFromUI:Boolean = binding.swHasCar.isChecked

        val data: MutableMap<String, Any> = HashMap();
        data["name"] = nameFromUI
        data["gpa"] = gpaFromUI
        data["hasCar"] = hasCarFromUI

        db.collection("students")
            .document(this.stuID)
            .set(data)
            .addOnSuccessListener { docRef ->
                Log.d("TESTING", "Document successfully updated")
                val snackbar = Snackbar.make(binding.root, "Update success, check Firebase console!", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            .addOnFailureListener { ex ->
                Log.e("TESTING", "Exception occurred while adding a document : $ex", )
            }
    }

    fun addStudent() {
        // get values from form
        val nameFromUI:String = binding.etName.text.toString()
        val gpaFromUI:Double = binding.etGpa.text.toString().toDouble()
        val hasCarFromUI:Boolean = binding.swHasCar.isChecked

        val data: MutableMap<String, Any> = HashMap();
        data["name"] = nameFromUI
        data["gpa"] = gpaFromUI
        data["hasCar"] = hasCarFromUI

        db.collection("students")
            .add(data)
            .addOnSuccessListener { docRef ->
                Log.d("TESTING", "Document successfully updated")
                val snackbar = Snackbar.make(binding.root, "Add success, check Firebase console!", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            .addOnFailureListener { ex ->
                Log.e("TESTING", "Exception occurred while adding a document : $ex", )
            }
    }
}