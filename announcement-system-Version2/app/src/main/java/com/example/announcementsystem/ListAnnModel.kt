package com.example.announcementsystem

import com.google.gson.annotations.SerializedName

class ListAnnModel {

    @SerializedName("data")
    var data: ArrayList<AnnModel> = ArrayList()

}