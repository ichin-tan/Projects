package com.example.property_app_g03.models

import com.google.firebase.firestore.DocumentId

data class Property(
    var rent: Double = 0.0,
    var address: String = "",
    var bedroomCount: Int = 0,
    var owner: String = "",
    var imgUrl: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    @DocumentId
    var propertyId: String = "",
    @JvmField
    var isAvailable: Boolean = true
)