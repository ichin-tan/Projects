package com.example.property_app_g03

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.property_app_g03.adapters.PropertyAdapter
import com.example.property_app_g03.databinding.ActivityPropertyListBinding
import com.example.property_app_g03.interfaces.ClickDetectorInterface
import com.example.property_app_g03.models.Property
import com.example.property_app_g03.models.RenterUser
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PropertyListActivity : AppCompatActivity(), ClickDetectorInterface {

    lateinit var binding: ActivityPropertyListBinding
    lateinit var renterUser: RenterUser
    var arrWatchListedProperties: MutableList<Property> = mutableListOf()
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth
    lateinit var adapter:PropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityPropertyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.auth = Firebase.auth
        this.supportActionBar!!.setTitle("Watchlisted Properties")
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.adapter = PropertyAdapter(this.arrWatchListedProperties, this)
        this.binding.rv.adapter = adapter
        this.binding.rv.layoutManager = LinearLayoutManager(this)
        this.binding.rv.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        this.getRenterUser()
    }

    fun getRenterUser() {
        this.db.collection("renterusers")
            .document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document: DocumentSnapshot ->
                val renterUser: RenterUser? = document.toObject(RenterUser::class.java)
                if (renterUser != null) {
                    this.renterUser = renterUser
                    this.getWatchlistedPropertiesOfRenter()
                }
            }.addOnFailureListener { exception ->
                Log.w("", "Error getting documents.", exception)
            }
    }

    fun getWatchlistedPropertiesOfRenter() {
        for(propertyId in this.renterUser.watchlistedProperties) {
            this.db.collection("properties")
                .document(propertyId)
                .get()
                .addOnSuccessListener {
                        document:DocumentSnapshot ->
                    val propertyFromDB: Property? = document.toObject(Property::class.java)
                    if(propertyFromDB != null) {
                        this.arrWatchListedProperties.add(propertyFromDB)
                        this.adapter.notifyDataSetChanged()
                    }
                }.addOnFailureListener {
                        exception ->
                    Log.w("TESTING", "Error getting documents.", exception)
                }
        }
    }

    override fun deleteRow(position: Int) {
        var propertyIdToRemove = this.arrWatchListedProperties[position].propertyId
        this.renterUser.watchlistedProperties.remove(propertyIdToRemove)
        val data: MutableMap<String, Any> = HashMap();
        data["watchlistedProperties"] = this.renterUser.watchlistedProperties
        db.collection("renterusers")
            .document(auth.currentUser!!.uid)
            .set(data)
            .addOnSuccessListener { docRef ->
                Snackbar.make(binding.root, "This property is removed from your watchlist!!", Snackbar.LENGTH_LONG).show()
                this.arrWatchListedProperties.removeAt(position)
                this.adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { ex ->
                Log.e("TESTING", "Exception occurred while removing a document : $ex", )
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