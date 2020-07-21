package com.example.announcementsystem

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.nio.file.Paths

class AddingActivity : AppCompatActivity() {

    lateinit var option: Spinner
    var coursesList = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding)


        val addB = findViewById<Button>(R.id.addB)
        var bundle: Bundle? = intent.extras
        val USER_ID: Int? = bundle?.getInt("USER_ID")
        val AUTHORITY: Boolean? = bundle?.getBoolean("AUTHORITY")

        option = findViewById(R.id.addCourseSP) as Spinner

        var jsonString = loadJson("courses.json", this)
        var courses = Gson().fromJson<ListCourseModel>(jsonString, ListCourseModel::class.java)

        for (course in courses.data) {
            if (course.lecturerID!!.contains(USER_ID!!)) {
                coursesList.add(course.name)
            }
        }

        option.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coursesList)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                //options.get(position)
            }
        }

        addB.setOnClickListener {
            //Toast.makeText(applicationContext, "CLICKED", Toast.LENGTH_LONG).show()

            var annJsonObj = JSONObject().put("date", "date111")
                .put("course", "course111")
                .put("topic", "topic111")
                .put("message", "message111")

            var json = JSONObject()
            json.put("data", JSONArray().put(annJsonObj))

            Log.d("myTag", json.toString())

            //saveJson(json.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveJson(jsonString: String){

        val path = System.getProperty("user.dir") + "\\src\\announcements.json"

        val output: Writer
        output = BufferedWriter(FileWriter(File("announcements.json")))
        output.write(jsonString)
        output.close()
    }

    private fun loadJson(fileName: String, context: Context): String? {
        var input: InputStream? = null
        var jsonString: String

        try {
            //Create InputStream
            input = context.assets.open(fileName)
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
}
