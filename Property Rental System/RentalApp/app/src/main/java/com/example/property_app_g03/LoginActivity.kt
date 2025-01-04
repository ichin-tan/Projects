package com.example.property_app_g03

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.property_app_g03.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.setTitle("LOGIN")
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        auth = Firebase.auth
        this.binding.btnLogin.setOnClickListener {
            this.login()
        }
    }

    fun login() {
        val emailFromUI = binding.etEmail.text.toString()
        val passwordFromUI = binding.etPassword.text.toString()

        auth.signInWithEmailAndPassword(emailFromUI, passwordFromUI)
            .addOnCompleteListener(this) {
                    task ->
                if (task.isSuccessful) {

                    if(auth.currentUser != null) {
                        this.db.collection("renterusers")
                            .document(auth.currentUser!!.uid)
                            .get()
                            .addOnSuccessListener {
                                    document: DocumentSnapshot ->
                                if(document.exists()) {
                                    val intent: Intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    auth.signOut()
                                    Snackbar.make(binding.root, "Only renters are allowed in this app! Please login to landlord app!", Snackbar.LENGTH_LONG).show()
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.w("", "Error getting documents.", exception)
                            }
                    }
                } else {
                    Snackbar.make(binding.root, "Login Failed!! - ${task.exception?.message.toString()}", Snackbar.LENGTH_LONG).show()
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}