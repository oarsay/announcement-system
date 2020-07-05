package com.example.deneme

import com.google.gson.annotations.SerializedName

class AnnModel {

    @SerializedName("date")
    var date: String = ""

    @SerializedName("course")
    var course: String = ""

    @SerializedName("topic")
    var topic: String = ""

    @SerializedName("message")
    var message: String = ""
}