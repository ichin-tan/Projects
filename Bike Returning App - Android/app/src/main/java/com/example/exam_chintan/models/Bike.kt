package com.example.exam_chintan.models

import com.google.firebase.firestore.DocumentId

data class Bike(
    @DocumentId
    var bikeId: String = "",
    @JvmField
    var isReturned: Boolean = false,
    var name: String = "",
    var address: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0
)