package com.example.property_app_g03

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.requestLocationUpdates
import androidx.core.view.isVisible
import com.example.property_app_g03.databinding.ActivityAddPropertyBinding
import com.example.property_app_landlord_g03.models.LandlordUser
import com.example.property_app_landlord_g03.models.Property
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class AddPropertyActivity : AppCompatActivity() {

    lateinit var binding:ActivityAddPropertyBinding
    var db = Firebase.firestore
    lateinit var geocoder: Geocoder
    private lateinit var auth: FirebaseAuth
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        // TODO: instantiate the geocoder class
        geocoder = Geocoder(applicationContext, Locale.getDefault())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var isUpdate = intent.hasExtra("PROPERTY")

        if(isUpdate == true) {
            binding.buttonAllProperties.isVisible = false
            binding.buttonLogout.isVisible = false
            var propertyToUpdate = intent.getSerializableExtra("PROPERTY") as Property
            binding.etAddress.setText(propertyToUpdate.address)
            binding.etImgURL.setText(propertyToUpdate.imgUrl)
            binding.etMonthlyPrice.setText(propertyToUpdate.rent.toString())
            binding.etBedroom.setText(propertyToUpdate.bedroomCount.toString())
            binding.buttonAddToDatabase.setText("UPDATE")
        }

        binding.buttonAddToDatabase.setOnClickListener {
            if(isUpdate == true) {
                this.updateProperty()
            } else {
                this.addProperty()
            }
        }

        binding.buttonAllProperties.setOnClickListener {
            val intent: Intent = Intent(this, PropertyListActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogout.setOnClickListener {
            auth.signOut()
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonShowLocation.setOnClickListener {
            if (checkLocationPermission()) {
                checkGpsEnabled()
            } else {
                requestLocationPermission()
            }
        }
    }

    fun addProperty() {
        Snackbar.make(binding.root, "Inserting property into firestore...", Snackbar.LENGTH_LONG).show()
        val data: MutableMap<String, Any> = HashMap();
        data["address"] = binding.etAddress.text.toString()
        data["bedroomCount"] = binding.etBedroom.text.toString().toIntOrNull() ?: 0
        data["imgUrl"] = binding.etImgURL.text.toString()
        data["rent"] = binding.etMonthlyPrice.text.toString().toDoubleOrNull() ?: 0.0
        if (this.getLatLongFromAddress() != null) {
            data["latitude"] = this.getLatLongFromAddress()!!.latitude
            data["longitude"] = this.getLatLongFromAddress()!!.longitude
        }
        if (auth.currentUser != null) {
            data["owner"] = auth.currentUser!!.uid
        }
        data["isAvailable"] = true
        db.collection("properties")
            .add(data)
            .addOnSuccessListener { docRef ->
                Log.d("TESTING", "Document successfully added with ID : ${docRef.id}")
                db.collection("landlordusers")
                    .document(auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener { document: DocumentSnapshot ->
                        val landlordUser: LandlordUser? = document.toObject(LandlordUser::class.java)
                        if(landlordUser != null) {
                            landlordUser.ownedProperties.add(docRef.id)
                            val data: MutableMap<String, Any> = HashMap();
                            data["ownedProperties"] = landlordUser.ownedProperties
                            db.collection("landlordusers")
                                .document(auth.currentUser!!.uid)
                                .set(data)
                                .addOnSuccessListener { docRef ->
                                    Log.d("TESTING", "Document successfully updated")
                                    Snackbar.make(binding.root, "This property is added in your list!!", Snackbar.LENGTH_LONG).show()
                                }
                                .addOnFailureListener { ex ->
                                    Log.d("TESTING", "Exception occurred while adding a document : $ex", )
                                }
                        }
                    }.addOnFailureListener { ex ->
                        Log.d("TESTING", "Exception occurred while adding a document : $ex", )
                    }
            }
            .addOnFailureListener { ex ->
                Log.d("TESTING", "Exception occurred while adding a document : $ex", )
            }
    }

    fun getLatLongFromAddress(): LatLng? {
        val addressFromUI = binding.etAddress.text.toString()
        try {
            val searchResults: MutableList<Address>? =
                geocoder.getFromLocationName(addressFromUI, 1)
            if (searchResults == null) {
                Snackbar.make(
                    binding.root,
                    "Could not connect to geocoding services, or no results found",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (searchResults.isEmpty()) {
                Snackbar.make(binding.root, "No coordinates found", Snackbar.LENGTH_LONG).show()
            } else {
                var foundAddress: Address = searchResults.get(0)
                val latlng: LatLng = LatLng(foundAddress.latitude, foundAddress.longitude)
                return latlng
            }
        } catch (ex: Exception) {
            return null
        }
        return null
    }

    fun populateAddressFromCurrentCoordinates(lat: Double, lon: Double) {
        try {
            val searchResults: MutableList<Address>? =
                geocoder.getFromLocation(lat, lon, 1)
            if (searchResults == null) {
                Snackbar.make(binding.root, "No address found", Snackbar.LENGTH_LONG).show()
                return
            }
            if (searchResults.isEmpty()) {
                Snackbar.make(binding.root, "No address found", Snackbar.LENGTH_LONG).show()
                return
            }
            var foundLocation: Address = searchResults.get(0)
            val output = "${foundLocation.subThoroughfare} ${foundLocation.thoroughfare} ${foundLocation.locality} ${foundLocation.adminArea} ${foundLocation.countryName}"
            binding.etAddress.setText(output)
        } catch (ex: Exception) {
            Snackbar.make(binding.root, "No address found - ${ex.message}", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun fetchCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                Log.d("TAGGGGG", "lat - ${location.latitude} lon - ${location.longitude}")
                this.populateAddressFromCurrentCoordinates(location.latitude, location.longitude)
            } else {
                Snackbar.make(binding.root, "No location found", Snackbar.LENGTH_LONG).show()
            }
        }.addOnFailureListener { exception ->
            Snackbar.make(binding.root, "No location found", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun checkLocationPermission(): Boolean {
        val fineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return fineLocation == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun checkGpsEnabled() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)

        client.checkLocationSettings(builder.build())
            .addOnSuccessListener {
                fetchCurrentLocation()
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(this, 1)
                    } catch (e: Exception) {
                    }
                }
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkGpsEnabled()
            } else {
                Snackbar.make(binding.root, "Location permission denied. Please enable it in settings.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    fun updateProperty() {
        val data: MutableMap<String, Any> = HashMap();
        data["address"] = binding.etAddress.text.toString()
        data["bedroomCount"] = binding.etBedroom.text.toString().toIntOrNull() ?: 0
        data["imgUrl"] = binding.etImgURL.text.toString()
        data["rent"] = binding.etMonthlyPrice.text.toString().toDoubleOrNull() ?: 0.0
        if (this.getLatLongFromAddress() != null) {
            data["latitude"] = this.getLatLongFromAddress()!!.latitude
            data["longitude"] = this.getLatLongFromAddress()!!.longitude
        }
        if (auth.currentUser != null) {
            data["owner"] = auth.currentUser!!.uid
        }
        var propertyToUpdate = intent.getSerializableExtra("PROPERTY") as Property
        data["isAvailable"] = propertyToUpdate.isAvailable
        db.collection("properties")
            .document(propertyToUpdate.propertyId)
            .set(data)
            .addOnSuccessListener { docRef ->
                Snackbar.make(binding.root, "Property Updated!", Snackbar.LENGTH_LONG).show()
            }.addOnFailureListener {
                Snackbar.make(binding.root, "Something went wrong!", Snackbar.LENGTH_LONG).show()
            }
    }
}

