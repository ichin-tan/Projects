package com.example.property_app_landlord_g03.models

import com.google.firebase.firestore.DocumentId

data class LandlordUser (
    var ownedProperties: MutableList<String> = mutableListOf(),
    @DocumentId
    var landlordUserID: String = ""
)