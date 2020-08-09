package com.example.announcementsystem

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.RequiresApi
import androidx.core.view.get
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_adding.*
import kotlinx.android.synthetic.main.activity_second.*
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddingActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    var coursesList = ArrayList<String?>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding)
        supportActionBar?.hide()


        val addB = findViewById<Button>(R.id.addB)
        val addTopicET = findViewById<EditText>(R.id.addTopicET)
        val addMessageET = findViewById<EditText>(R.id.addMessageET)
        var bundle: Bundle? = intent.extras
        val USER_ID: Int? = bundle?.getInt("USER_ID")
        val AUTHORITY: Boolean? = bundle?.getBoolean("AUTHORITY")

        spinner = findViewById(R.id.addCourseSP)

        var database = FirebaseDatabase.getInstance().reference

        database.child("courses").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "FIREBASE OKUMA BASARISIZ", LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {

                //coursesList.clear()
                if (p0.exists()) {

                    for (c in p0.children) {
                        var course = c.getValue(CourseModel::class.java)!!

                        if (course.lecturerID!!.contains(USER_ID)) {
                            coursesList.add(course.name)
                        }
                    }
                }

                spinner.adapter = ArrayAdapter<String>(this@AddingActivity, android.R.layout.simple_list_item_activated_1, coursesList)
            }
        })


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }
        addB.setOnClickListener {

            saveAnnouncement(addTopicET.text.toString(), addMessageET.text.toString())

        }
    }

    private fun clearScreen(){

        addTopicET.text = null
        addMessageET.text = null
    }

    private fun saveAnnouncement(topic: String, message: String) {

        if(spinner.selectedItem == null){
            Toast.makeText(applicationContext, "SPINNER IS NULL", LENGTH_LONG).show()
            return
        }

        if (topic.trim().isEmpty() || message.trim().isEmpty()) {
            Toast.makeText(applicationContext, "Lütfen tüm bilgileri doldurunuz", LENGTH_LONG).show()
            return
        }

        var database = FirebaseDatabase.getInstance().getReference("announcements")

        var annID = database.push().key

        var announcement = AnnFireBaseModel(annID!!, getDate(), (spinner.selectedItem.toString()), topic, message)
        announcement.annID = annID!!

        database.child(annID).setValue(announcement).addOnCompleteListener{
            Toast.makeText(applicationContext, "Duyuru başarıyla eklendi", LENGTH_LONG).show()
        }

        clearScreen()
        this.finish()
    }

    private fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }

}
