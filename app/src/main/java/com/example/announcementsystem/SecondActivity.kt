package com.example.announcementsystem

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.deneme.ListAnnModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.announcement.view.*
import java.io.InputStream
import java.lang.Exception

class SecondActivity : AppCompatActivity() {

    var announcementsList = ArrayList<Announcements>()
    var adapter: AnnListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        //supportActionBar?.hide()

        val jsonString = loadJson(this)

        var anns = Gson().fromJson<ListAnnModel>(jsonString, ListAnnModel::class.java)

        for(annJson in anns.data) {
            announcementsList.add(
                Announcements(
                    annJson.date,
                    annJson.course,
                    annJson.topic,
                    annJson.message
                )
            )
        }

        adapter = AnnListAdapter(this, announcementsList)
        announcmentsLV.adapter = adapter
    }

    private fun loadJson(context: Context): String? {
        var input: InputStream? = null
        var jsonString: String

        try {
            //Create InputStream
            input = context.assets.open("announcements.json")
            val size = input.available()

            //Create a buffer with the size
            val buffer = ByteArray(size)

            //Read data from InputStream into the Buffer
            input.read(buffer)

            //Create a json string
            jsonString = String(buffer)
            return jsonString
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            //Must close the stream
            input?.close()
        }
        return null
    }

    class AnnListAdapter : BaseAdapter {

        var announcementsList = ArrayList<Announcements>()
        var context: Context? = null

        constructor(context: Context, announcementsList: ArrayList<Announcements>) : super() {
            this.announcementsList = announcementsList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var posAnn = announcementsList[position]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var announcement = inflator.inflate(R.layout.announcement, null)
            announcement.annDateTV.text = posAnn.date
            announcement.annCourseTV.text = posAnn.course
            announcement.annTopicTV.text = posAnn.topic

            announcement.annTopicTV.setOnClickListener{
                var intent = Intent (context, DisplayActivity::class.java)
                intent.putExtra("date", posAnn.date)
                intent.putExtra("course", posAnn.course)
                intent.putExtra("topic", posAnn.topic)
                intent.putExtra("message", posAnn.message)

                context!!.startActivity(intent)
            }
            return announcement
        }

        override fun getItem(position: Int): Any {
            return announcementsList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return announcementsList.size
        }


    }


}
