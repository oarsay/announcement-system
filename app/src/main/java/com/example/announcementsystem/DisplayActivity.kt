package com.example.announcementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_display.*

class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        var bundle: Bundle? = intent.extras
        var date: String? = bundle?.getString("date")
        var course: String? = bundle?.getString("course")
        var topic: String? = bundle?.getString("topic")
        var message: String? = bundle?.getString("message")

        displayDateTV.text = date
        displayCourseTV.text = course
        displayTopicTV.text = topic
        displayMessageTV.text = message
    }
}
