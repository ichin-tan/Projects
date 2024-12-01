package com.example.firestorewithrv.models

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

//data class Student(
//    @DocumentId var id: String = "",
//    var name: String = "",
//    var gpa: Double = 0.0,
//    var hasCar: Boolean = false,
//    var imgUrl: String = "https://via.placeholder.com/250",
//)

class Student : Serializable {
    @DocumentId var id: String = ""
    var name: String = ""
    var gpa: Double = 0.0
    var hasCar: Boolean = false
    var imgUrl: String = "https://via.placeholder.com/250"
}