package com.example.announcementsystem

import com.google.gson.annotations.SerializedName

class ListUserModel {

    @SerializedName("data")
    var data: ArrayList<UserModel> = ArrayList()

}