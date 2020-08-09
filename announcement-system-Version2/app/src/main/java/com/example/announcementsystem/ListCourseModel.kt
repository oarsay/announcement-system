package com.example.announcementsystem

import com.google.gson.annotations.SerializedName

class ListCourseModel {

    @SerializedName("data")
    var data: ArrayList<CourseModel> = ArrayList()
}