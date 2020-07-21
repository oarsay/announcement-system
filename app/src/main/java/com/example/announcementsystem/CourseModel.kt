package com.example.announcementsystem

import com.google.gson.annotations.SerializedName

class CourseModel {

    @SerializedName("name")
    var name: String = ""

    @SerializedName("lecturerID")
    var lecturerID: IntArray? = null

    @SerializedName("studentID")
    var studentID: IntArray? = null
}