package com.example.announcementsystem

class Announcements {

    var date: String? = null
    var course: String? = null
    var topic: String? = null
    var message: String? = null

    constructor(date: String?, course: String?, topic: String?, message: String?){
        this.date = date
        this.course = course
        this.topic = topic
        this.message = message
    }
}