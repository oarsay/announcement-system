package com.example.announcementsystem

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.announcement.view.*
import kotlin.collections.ArrayList

class SecondActivity : AppCompatActivity() {

    var announcementsList = ArrayList<AnnFireBaseModel>()
    var adapter: AnnListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        supportActionBar?.hide()

        var bundle: Bundle? = intent.extras
        val bellB = findViewById<ImageView>(R.id.bellButtonIV)
        val id = findViewById<TextView>(R.id.idTV)
        val USER_ID: Int? = bundle?.getInt("USER_ID")
        val AUTHORITY: Boolean? = bundle?.getBoolean("AUTHORITY")

        if(AUTHORITY!!){
            bellB.visibility = View.VISIBLE
            id.text = "Personel: $USER_ID"
        }
        else{
            bellB.visibility = View.INVISIBLE
            id.text = "Öğrenci: $USER_ID"
        }


        bellB.setOnClickListener {
            val intent = Intent(this, AddingActivity::class.java)
            intent.putExtra("USER_ID", USER_ID)
            intent.putExtra("AUTHORITY", AUTHORITY)
            startActivity(intent)
        }


        var database = FirebaseDatabase.getInstance().reference

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "FIREBASE OKUMA BASARISIZ", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {

                announcementsList.clear()
                    //Toast.makeText(applicationContext, "FIREBASE OKUMA BASARILI", Toast.LENGTH_LONG).show()

                if (p0.exists()) {

                    Log.d("SIZE :  ", "${announcementsList.size}")

                    for (a in p0.child("announcements").children) {
                        for (c in p0.child("courses").children) {
                            var ann = a.getValue(AnnFireBaseModel::class.java)!!
                            //var ann2 = AnnFireBaseModel(ann.annID, ann.date, ann.course, ann.topic, ann.message)
                            var course = c.getValue(CourseModel::class.java)!!
                            //var course2 = CourseModel(course.name, course.lecturerID, course.studentID)

                            if(ann.course == course.name && (course.lecturerID!!.contains(USER_ID) || course.studentID!!.contains(USER_ID))){
                                announcementsList.add(ann)
                            }

                        }
                    }
                }

                announcementsList.reverse()
                adapter = AnnListAdapter(this@SecondActivity , announcementsList)
                announcmentsLV.adapter = adapter
            }
        })



    }

    class AnnListAdapter : BaseAdapter {

        var announcementsList2 = ArrayList<AnnFireBaseModel>()
        var context: Context? = null

        constructor(context: Context, announcementsList: ArrayList<AnnFireBaseModel>) : super() {
            this.announcementsList2 = announcementsList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var posAnn = announcementsList2[position]
            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var announcement = inflator.inflate(R.layout.announcement, null)
            announcement.annDateTV.text = posAnn.date
            announcement.annCourseTV.text = posAnn.course
            announcement.annTopicTV.text = posAnn.topic

            announcement.setOnClickListener {
                var intent = Intent(context, DisplayActivity::class.java)
                intent.putExtra("date", posAnn.date)
                intent.putExtra("course", posAnn.course)
                intent.putExtra("topic", posAnn.topic)
                intent.putExtra("message", posAnn.message)

                context!!.startActivity(intent)
            }
            return announcement
        }

        override fun getItem(position: Int): Any {
            return announcementsList2[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return announcementsList2.size
        }

    }

}
