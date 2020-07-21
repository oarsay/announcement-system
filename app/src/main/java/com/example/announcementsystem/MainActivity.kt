package com.example.announcementsystem

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val loginB = findViewById<Button>(R.id.loginB)
        val userName = findViewById<EditText>(R.id.userNameET)
        val password = findViewById<EditText>(R.id.passwordET)

        loginB.setOnClickListener {

            var jsonString = loadJson("usersAcademic.json" , this)
            var users = Gson().fromJson<ListUserModel>(jsonString, ListUserModel::class.java)

            for(user in users.data) {
                if(user.id.toString() == userName.text.toString()){
                    if(user.password.toString() == password.text.toString()){

                        userNameET.text = null
                        password.text = null

                        Toast.makeText(applicationContext, "Personel Girişi: ${user.name}", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, SecondActivity::class.java)
                        intent.putExtra("USER_ID", user.id)
                        intent.putExtra("AUTHORITY", true)
                        startActivity(intent)
                    }
                }
            }

            jsonString = loadJson("usersStudent.json" , this)
            users = Gson().fromJson<ListUserModel>(jsonString, ListUserModel::class.java)

            for(user in users.data) {
                if(user.id.toString() == userName.text.toString()){
                    if(user.password.toString() == password.text.toString()){

                        userNameET.text = null
                        password.text = null

                        Toast.makeText(applicationContext, "Öğrenci Girişi: ${user.name}", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, SecondActivity::class.java)
                        intent.putExtra("USER_ID", user.id)
                        intent.putExtra("AUTHORITY", false)
                        startActivity(intent)
                    }
                }
            }

        }
    }

    private fun loadJson(fileName:String , context: Context): String? {
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
