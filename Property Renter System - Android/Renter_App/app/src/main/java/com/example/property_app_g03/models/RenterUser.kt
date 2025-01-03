package com.example.property_app_g03.models

import com.google.firebase.firestore.DocumentId

data class RenterUser (
    var watchlistedProperties: MutableList<String> = mutableListOf(),
    @DocumentId
    var renterUserID: String = ""
)