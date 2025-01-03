package com.example.property_app_g03
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.property_app_g03.databinding.ActivityHomeBinding
import com.example.property_app_g03.models.Property
import com.example.property_app_g03.models.RenterUser
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var binding:ActivityHomeBinding
    lateinit var gMap: GoogleMap
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth
    var arrProperties: MutableList<Property> = mutableListOf()
    var selectedPropertyId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setTitle("")
        auth = Firebase.auth
        val mapFragment = binding.mapFragmentContainer.getFragment<SupportMapFragment>()
        mapFragment.getMapAsync(this)
        this.getAllPropertiesFromFirebase()

        this.binding.btnAddToWatchList.setOnClickListener {
            if(auth.currentUser == null) {
                Snackbar.make(binding.root, "Please login to add it to your watch list!!", Snackbar.LENGTH_LONG).show()
            } else {
                this.addPropertyToCurrentUserWatchList()
            }
        }

        this.binding.btnSearch.setOnClickListener {
            val filteredPrice:Double? = binding.etSearchPrice.text.toString().toDoubleOrNull()
            if(filteredPrice != null) {
                if(filteredPrice == 0.0) {
                    this.getAllPropertiesFromFirebase()
                } else {
                    this.getFilteredPropertiesFromFirebase(filteredPrice)
                }
            } else {
                Snackbar.make(binding.root, "Please enter a valid price!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        gMap = map
        gMap.uiSettings.isZoomControlsEnabled = true
        gMap.uiSettings.isZoomGesturesEnabled = true

        gMap.setOnMarkerClickListener { marker ->
            this.selectedPropertyId = marker.title.toString()
            this.getAndShowDataForSelectedProperty()
            gMap.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
            true
        }
    }

    fun addMarkersOnMapFor(properties: MutableList<Property>) {
        if(gMap != null) {
            gMap.clear()
            for(property in properties) {
                var latitude = property.latitude
                var longitude = property.longitude
                var propertyLocation = LatLng(latitude,longitude)
                val markerOptions = MarkerOptions()
                    .position(propertyLocation)
                    .title(property.propertyId)
                gMap.addMarker(markerOptions)
            }
        } else {
            this.addMarkersOnMapFor(properties)
        }
    }

    fun getAndShowDataForSelectedProperty() {
        db.collection("properties")
            .document(this.selectedPropertyId)
            .get()
            .addOnSuccessListener {
                    document: DocumentSnapshot ->
                val selectedProperty: Property? = document.toObject(Property::class.java)
                val imageId = this.resources.getIdentifier(selectedProperty?.imgUrl, "drawable", this.packageName)
                this.binding.imgProperty.setImageResource(imageId)
                this.binding.tvRent.text = "Rent:- $${selectedProperty?.rent.toString()}"
                this.binding.tvAddress.text = "Address:- ${selectedProperty?.address}"
                this.binding.tvBedRoomCount.text = "Bedrooms:- ${selectedProperty?.bedroomCount.toString()}"
            }.addOnFailureListener {
                    exception ->
                Log.w("TESTING", "Error getting documents.", exception)
            }
    }

    fun getAllPropertiesFromFirebase() {
        this.db.collection("properties")
            .whereEqualTo("isAvailable", true)
            .get()
            .addOnSuccessListener {
                    results: QuerySnapshot ->
                this.arrProperties.clear()
                for (document: QueryDocumentSnapshot in results) {
                    val property:Property = document.toObject(Property::class.java)
                    this.arrProperties.add(property)
                }
                this.addMarkersOnMapFor(this.arrProperties)
                this.selectedPropertyId = this.arrProperties.random().propertyId
                this.getAndShowDataForSelectedProperty()
            }
            .addOnFailureListener { exception ->
                Log.w("", "Error getting documents.", exception)
            }
    }

    fun getFilteredPropertiesFromFirebase(price: Double) {
        this.db.collection("properties")
            .whereLessThanOrEqualTo("rent", price)
            .whereEqualTo("isAvailable",true)
            .get()
            .addOnSuccessListener {
                    results: QuerySnapshot ->
                this.arrProperties.clear()
                for (document: QueryDocumentSnapshot in results) {
                    val property:Property = document.toObject(Property::class.java)
                    this.arrProperties.add(property)
                }
                this.addMarkersOnMapFor(this.arrProperties)
                if(!this.arrProperties.isEmpty()) {
                    this.selectedPropertyId = this.arrProperties.random().propertyId
                    this.getAndShowDataForSelectedProperty()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("", "Error getting documents.", exception)
            }
    }

    fun addPropertyToCurrentUserWatchList() {
        this.db.collection("renterusers")
            .document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                    document: DocumentSnapshot ->
                val renterUser: RenterUser? = document.toObject(RenterUser::class.java)
                if(renterUser != null) {
                    if(renterUser.watchlistedProperties.contains(this.selectedPropertyId)) {
                        Snackbar.make(binding.root, "This property is already in your watchlist!!", Snackbar.LENGTH_LONG).show()
                    } else {
                        renterUser.watchlistedProperties.add(this.selectedPropertyId)
                        // Now its added, so should update in firebase
                        val data: MutableMap<String, Any> = HashMap();
                        data["watchlistedProperties"] = renterUser.watchlistedProperties
                        db.collection("renterusers")
                            .document(auth.currentUser!!.uid)
                            .set(data)
                            .addOnSuccessListener { docRef ->
                                Log.d("TESTING", "Document successfully updated")
                                Snackbar.make(binding.root, "This property is added in your watchlist!!", Snackbar.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { ex ->
                                Log.e("TESTING", "Exception occurred while adding a document : $ex", )
                            }
                    }
                } else {
                    Snackbar.make(binding.root, "Couldn't find the renter user!!", Snackbar.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("", "Error getting documents.", exception)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_login -> {
                Log.d("","Login Tapped")
                if(auth.currentUser == null) {
                    val intent: Intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(binding.root, "You are already logged in!!", Snackbar.LENGTH_LONG).show()
                }
                return true
            }
            R.id.mi_my_watchlist -> {
                Log.d("","My Watch List Tapped")
                if(auth.currentUser == null) {
                    Snackbar.make(binding.root, "No user is logged in!!", Snackbar.LENGTH_LONG).show()
                } else {
                    val intent: Intent = Intent(this, PropertyListActivity::class.java)
                    startActivity(intent)
                }
                return true

            }
            R.id.mi_logout -> {
                Log.d("","Logout Tapped")
                if(auth.currentUser == null) {
                    Snackbar.make(binding.root, "No user is logged in!!", Snackbar.LENGTH_LONG).show()
                } else {
                    auth.signOut()
                    Snackbar.make(binding.root, "Successfully Logged out!!", Snackbar.LENGTH_LONG).show()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
