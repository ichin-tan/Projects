package com.example.exam_chintan

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exam_chintan.adapters.BikeAdapter
import com.example.exam_chintan.databinding.ActivityMainBinding
import com.example.exam_chintan.interfaces.ClickDetectorInterface
import com.example.exam_chintan.models.Bike
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity(), OnMapReadyCallback, ClickDetectorInterface {

    lateinit var binding:ActivityMainBinding
    lateinit var gMap: GoogleMap
    lateinit var geocoder: Geocoder
    var db = Firebase.firestore
    lateinit var auth: FirebaseAuth
    var arrBikes: MutableList<Bike> = mutableListOf()
    lateinit var adapter:BikeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setTitle("Admin App")
        geocoder = Geocoder(applicationContext, Locale.getDefault())
        auth = Firebase.auth
        val mapFragment = binding.mapFragmentContainer.getFragment<SupportMapFragment>()
        mapFragment.getMapAsync(this)

        this.adapter = BikeAdapter(this.arrBikes, this)
        this.binding.rv.adapter = adapter
        this.binding.rv.layoutManager = LinearLayoutManager(this)
        this.binding.rv.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miReportBycycle -> {
                Log.d("","Report bycycle tapped")
                this.generateRandomLocationAndShowBikeOnMap(this.generateTorontoLat(), this.generateTorontoLng())
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        gMap = map
        Log.d("MapReady", "Map is ready")
        gMap.uiSettings.isZoomControlsEnabled = true
        gMap.uiSettings.isZoomGesturesEnabled = true
        this.zoomMapToToronto()
        this.getAllBikesFromFirebaseAndAddMarkers()
    }

    fun generateTorontoLat(): Double {
        val min = 43.58
        val max = 43.85
        return min + (max - min) * Math.random()
    }

    fun generateTorontoLng(): Double {
        val min = -79.64
        val max = -79.12
        return min + (max - min) * Math.random()
    }

    fun getAllBikesFromFirebaseAndAddMarkers() {
        this.db.collection("bikes")
            .get()
            .addOnSuccessListener {
                    results: QuerySnapshot ->
                this.arrBikes.clear()
                for (document: QueryDocumentSnapshot in results) {
                    val bike:Bike = document.toObject(Bike::class.java)
                    this.arrBikes.add(bike)
                    this.adapter.notifyDataSetChanged()
                }

                //Add Markers
                if(gMap != null) {
                    gMap.clear()
                    for(bike in this.arrBikes) {
                        if(bike.isReturned == false) {
                            var latitude = bike.lat
                            var longitude = bike.lon
                            var bikeLocation = LatLng(latitude,longitude)
                            val markerOptions = MarkerOptions()
                                .position(bikeLocation)
                                .title("${bike.address}")
                                .snippet("${bike.name}")
                            gMap.addMarker(markerOptions)
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("", "Error getting documents.", exception)
            }
    }

    fun generateRandomLocationAndShowBikeOnMap(lat: Double, lon: Double) {
        // First getting address
        var address = ""
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
            // In case I get null values, I provided optional values
            address = "${foundLocation.subThoroughfare ?: 404} ${foundLocation.thoroughfare ?: "optional street"} ${foundLocation.locality ?: "Toronto"} ${foundLocation.adminArea} ${foundLocation.countryName}"
        } catch (ex: Exception) {
            address = "404 optional street Toronto, Ontario"
            Snackbar.make(binding.root, "No address found - ${ex.message}", Snackbar.LENGTH_LONG).show()
        }

        // add it to firebase
        val data: MutableMap<String, Any> = HashMap();
        data["lat"] = lat
        data["lon"] = lon
        data["address"] = address
        data["isReturned"] = false
        var name = this.generateRandomName()
        data["name"] = name

        db.collection("bikes")
            .add(data)
            .addOnSuccessListener { docRef ->
                // Add to recyclerview

                var bike = Bike()
                bike.bikeId = docRef.id
                bike.lat = lat
                bike.lon = lon
                bike.name = name
                bike.address = address
                bike.isReturned = false
                this.arrBikes.add(bike)
                this.adapter.notifyDataSetChanged()

                // Add marker
                if (gMap != null) {
                    var bikeLocation = LatLng(lat,lon)
                    val markerOptions = MarkerOptions()
                        .position(bikeLocation)
                        .title("${address}")
                        .snippet("${name}")
                    gMap.addMarker(markerOptions)
                }
            }.addOnFailureListener { ex ->
                Log.e("TESTING", "Exception occurred while adding a document : $ex", )
            }
    }

    override fun returnCycle(pos: Int) {
        // Change property from database
        var bike = this.arrBikes[pos]
        val data: MutableMap<String, Any> = HashMap();
        data["lat"] = bike.lat
        data["lon"] = bike.lon
        data["address"] = bike.address
        data["isReturned"] = true
        data["name"] = bike.name
        db.collection("bikes")
            .document(bike.bikeId)
            .set(data)
            .addOnSuccessListener {
                this.getAllBikesFromFirebaseAndAddMarkers()
            }.addOnFailureListener { ex ->
                Log.e("TESTING", "Exception occurred while updating a document : $ex", )
            }
    }

    fun generateRandomName(): String {
        var name = "bike - "
        var randomNo = Random.nextInt(100, 999)
        return name + "${randomNo}"
    }

    fun zoomMapToToronto() {
        // I took avg of min max lat and lon
        val torontoLatLng = LatLng(43.72, -79.38)
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(torontoLatLng, 12f))
    }
}