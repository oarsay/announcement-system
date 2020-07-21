package com.example.announcementsystem

import com.google.gson.annotations.SerializedName

class UserModel {

    @SerializedName("name")
    var name: String = ""

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("password")
    var password: Int? = null
}