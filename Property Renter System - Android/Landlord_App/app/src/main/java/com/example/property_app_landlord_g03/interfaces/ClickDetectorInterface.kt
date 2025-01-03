package com.example.property_app_landlord_g03.interfaces

interface ClickDetectorInterface {
    fun deleteProperty(position: Int)
    fun updateProperty(position: Int)
    fun updateAvailableSwitch(position: Int, switchValue: Boolean)
}