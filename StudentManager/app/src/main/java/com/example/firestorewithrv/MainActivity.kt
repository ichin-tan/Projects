package com.example.firestorewithrv

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firestorewithrv.adapters.StudentAdapter
import com.example.firestorewithrv.databinding.ActivityMainBinding
import com.example.firestorewithrv.interfaces.ClickDetectorInterface
import com.example.firestorewithrv.models.Student
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(), ClickDetectorInterface {
    lateinit var binding: ActivityMainBinding

    lateinit var adapter: StudentAdapter

    var dataToDisplay:MutableList<Student> = mutableListOf()

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TESTING", "onCreate() executing....")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ------------------------------
        // RecyclerView Adapter
        // ------------------------------
        // initialize the adapter
        adapter = StudentAdapter(dataToDisplay, this)

        // ------------------------------
        // recyclerview configuration
        // ------------------------------
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        // ------------------------------
        // click handlers
        // ------------------------------

        binding.btnAddStudent.setOnClickListener {
            // Have to go to screen 2
            val intent = Intent(this@MainActivity, EditScreen::class.java)
            intent.putExtra("is_edit", false)
            startActivity(intent)
        }


        binding.btnSearch.setOnClickListener {
            // get search keyword from textbox
            val keywordFromUI: String = binding.etSearchText.text.toString()
            val snackbar = Snackbar.make(binding.root, "TODO: Searching for ${keywordFromUI}!", Snackbar.LENGTH_SHORT)
            snackbar.show()

            db.collection("students")
//                .whereEqualTo("name", binding.etSearchText.text.toString())
                .whereGreaterThanOrEqualTo("gpa", binding.etSearchText.text.toString().toDouble())
                .get().addOnSuccessListener {  results:QuerySnapshot ->

                val studentListFromDB:MutableList<Student> = mutableListOf()


                for (document in results) {
                    // convert the document to a Student object
                    val student:Student = document.toObject(Student::class.java)
                    // then add the document to the temporary list
                    studentListFromDB.add(student)
                }

                this.dataToDisplay.clear()
                this.dataToDisplay.addAll(studentListFromDB)
                this.adapter.notifyDataSetChanged()

                Log.d("TESTING", studentListFromDB.toString())
            }.addOnFailureListener { exception ->
                Log.w("TESTING", "Error getting documents.", exception)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        this.getDataFromDB()
    }

    fun getDataFromDB() {
        val snackbar = Snackbar.make(binding.root, "TODO: Getting all data!", Snackbar.LENGTH_SHORT)
        snackbar.show()

        db.collection("students").get().addOnSuccessListener { results: QuerySnapshot ->
            // create a temporary list of students
            val studentListFromDB:MutableList<Student> = mutableListOf()

            // for each document in the collection,
            for (document: QueryDocumentSnapshot in results) {
                // convert the document to a Student object
                val student:Student = document.toObject(Student::class.java)

                // then add the document to the temporary list
                studentListFromDB.add(student)
            }
            this.dataToDisplay.clear()
            this.dataToDisplay.addAll(studentListFromDB)
            this.adapter.notifyDataSetChanged()

        }.addOnFailureListener {

        }
    }

    // ---------------------------------------
    // click handlers for recyclerview rows
    // ---------------------------------------

    // click handler for UPDATE button in recycler row
    override fun updateRow(position: Int) {
        // Get the current item from the list of data
        val student: Student = this.dataToDisplay.get(position)

        val intent = Intent(this@MainActivity, EditScreen::class.java)
        intent.putExtra("student_id", student.id)
        intent.putExtra("is_edit", true)
        startActivity(intent)
    }

    // click handler for DELETE button in recycler row
    override fun deleteRow(position: Int) {
        var student = this.dataToDisplay.get(position)
        db.collection("students")
            // specify the document id of the item you want to delete
            .document(student.id)
            .delete()
            .addOnSuccessListener {
                val snackbar = Snackbar.make(binding.root, "Delete success, check Firebase console!", Snackbar.LENGTH_SHORT)
                snackbar.show()
                this.dataToDisplay.removeAt(position)
                this.adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                    e ->
                Log.w("TESTING", "ERROR deleting document", e)
            }
    }



    // ---------------------------------------
    // Helper functions to perform Firestore operations
    // ---------------------------------------

    fun loadData() {
        Log.d("TESTING", "Loading data")

        // TODO: Retrieve all documents from Firestore and update RecyclerView
    }

    fun searchByName(studentName:String) {
        // TODO: Retrieve matching documents from Firestore and update RV
    }



    fun searchByDocumentId(id:String) {
        // TODO: Retrieve document by id and add it to the RV
    }

    fun deleteDocument(docId:String) {
        // TODO: Code to delete a document using its document id
    }

}