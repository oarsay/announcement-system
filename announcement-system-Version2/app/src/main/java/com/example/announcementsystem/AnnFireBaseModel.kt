package com.example.announcementsystem

class AnnFireBaseModel
{
    var date: String? = null
    var course: String? = null
    var topic: String? = null
    var message: String? = null
    var annID: String? = null

    constructor(annID: String?, date: String?, course: String?, topic: String?, message: String?){
        this.annID = annID
        this.date = date
        this.course = course
        this.topic = topic
        this.message = message
    }

    constructor(){}
}




