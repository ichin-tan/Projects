package com.example.project_g02.models

import java.io.Serializable

class Lesson: Serializable {
    val name: String
    val length: String
    val imgFileName: String
    var isCompleted:Boolean = false
    val description: String
    val url: String

    constructor(name: String, length: String, imgFileName: String, description: String, url: String) {
        this.name = name
        this.length = length
        this.imgFileName = imgFileName
        this.description = description
        this.url = url
    }

    override fun toString(): String {
        return "name: ${this.name} \n isCompleted: ${this.isCompleted} \n"
    }
}