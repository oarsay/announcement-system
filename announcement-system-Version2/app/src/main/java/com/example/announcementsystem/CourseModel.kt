package com.example.announcementsystem

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class CourseModel {

    var name: String? = null
    var lecturerID: ArrayList<Int>? = null
    var studentID: ArrayList<Int>? = null

    constructor(name:String?, lecturerID:ArrayList<Int>, studentID:ArrayList<Int>){
        this.name = name
        this.lecturerID = lecturerID
        this.studentID = studentID
    }

    constructor(){}

}