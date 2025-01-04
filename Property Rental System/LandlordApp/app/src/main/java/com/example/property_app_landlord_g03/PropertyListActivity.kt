package com.example.property_app_g03

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.property_app_g03.adapters.PropertyAdapter
import com.example.property_app_g03.databinding.ActivityPropertyListBinding
import com.example.property_app_landlord_g03.interfaces.ClickDetectorInterface
import com.example.property_app_landlord_g03.models.LandlordUser
import com.example.property_app_landlord_g03.models.Property
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PropertyListActivity : AppCompatActivity(), ClickDetectorInterface {

    lateinit var binding: ActivityPropertyListBinding
    var arrProperties: MutableList<Property> = mutableListOf()
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth
    lateinit var adapter: PropertyAdapter
    lateinit var landlord: LandlordUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityPropertyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.auth = Firebase.auth
        this.adapter = PropertyAdapter(this.arrProperties, this)
        this.binding.recycleView.adapter = adapter
        this.binding.recycleView.layoutManager = LinearLayoutManager(this)
        this.binding.recycleView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        this.getLandlordProperties()
    }

    fun getLandlordProperties() {
        this.db.collection("landlordusers")
            .document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document: DocumentSnapshot ->
                var landlordUser: LandlordUser? = document.toObject(LandlordUser::class.java)
                if (landlordUser != null) {
                    this.landlord = landlordUser
                    this.arrProperties.clear()
                    for(propertyId in landlord!!.ownedProperties) {
                        this.db.collection("properties")
                            .document(propertyId)
                            .get()
                            .addOnSuccessListener {
                                    document:DocumentSnapshot ->
                                val propertyFromDB: Property? = document.toObject(Property::class.java)
                                if(propertyFromDB != null) {
                                    this.arrProperties.add(propertyFromDB)
                                    this.arrProperties.sortBy { it.propertyId }
                                    this.adapter.notifyDataSetChanged()
                                }
                            }.addOnFailureListener {
                                    exception ->
                                Log.d("TESTING", "Error getting documents.", exception)
                            }
                    }
                }
            }.addOnFailureListener { exception ->
                Log.d("", "Error getting documents.", exception)
            }
    }

    override fun deleteProperty(position: Int) {
        var propertyIdToRemove = this.arrProperties[position].propertyId
        this.landlord.ownedProperties.remove(propertyIdToRemove)
        val data: MutableMap<String, Any> = HashMap();
        data["ownedProperties"] = this.landlord.ownedProperties
        db.collection("landlordusers")
            .document(auth.currentUser!!.uid)
            .set(data)
            .addOnSuccessListener { docRef ->
                Snackbar.make(binding.root, "This property is removed from your watchlist!!", Snackbar.LENGTH_LONG).show()
                this.arrProperties.removeAt(position)
                this.adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { ex ->
                Log.e("TESTING", "Exception occurred while removing a document : $ex", )
            }
    }

    override fun updateProperty(position: Int) {
        val intent = Intent(this, AddPropertyActivity::class.java)
        intent.putExtra("PROPERTY", this.arrProperties[position])
        startActivity(intent)
    }

    override fun updateAvailableSwitch(position: Int, switchValue: Boolean) {
        var property = this.arrProperties[position]
        val data: MutableMap<String, Any> = HashMap();
        data["address"] = property.address
        data["bedroomCount"] = property.bedroomCount
        data["imgUrl"] = property.imgUrl
        data["rent"] = property.rent
        data["latitude"] = property.latitude
        data["longitude"] = property.longitude
        if (auth.currentUser != null) {
            data["owner"] = auth.currentUser!!.uid
        }
        data["isAvailable"] = switchValue
        db.collection("properties")
            .document(property.propertyId)
            .set(data)
            .addOnSuccessListener { docRef ->
//                Snackbar.make(binding.root, "Property Updated!", Snackbar.LENGTH_LONG).show()
            }.addOnFailureListener {
//                Snackbar.make(binding.root, "Something went wrong!", Snackbar.LENGTH_LONG).show()
            }
    }
}