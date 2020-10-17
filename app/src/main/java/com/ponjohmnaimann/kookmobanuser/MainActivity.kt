package com.ponjohmnaimann.kookmobanuser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    @SuppressLint("MissingPermission", "NewApi")
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {

        val pref = this.getPreferences(0)
        val editor = pref.edit()

        val loginName = pref.getString("name", "")
        val loginRank = pref.getString("rank", "")
        val loginUnit = pref.getString("unit", "")

        if (loginName != "" && loginRank != "" && loginUnit != "") {

            val loggedinIntent = Intent(this, LoggedInMainActivity::class.java)
            startActivity(loggedinIntent)

        }


        /*prefs = PreferenceUtil(applicationContext)
        val loginName = prefs.getString("name", null)
        val loginRank = prefs.getString("rank", null)
        val loginUnit = prefs.getString("unit", null)
        val prefSeed = prefs.getString("seed", null)
        val prefAdminID = prefs.getString("adminID", null)
        val prefDeviceID = prefs.getString("deviceID", null)

        if (loginName != null && loginRank != null && loginUnit != null
            && prefSeed != null && prefAdminID != null && prefDeviceID != null) {

            val loggedinIntent = Intent(this, LoggedInMainActivity::class.java)
            startActivity(loggedinIntent)
            loggedinIntent.putExtra("seed", prefSeed)
            loggedinIntent.putExtra("adminID", prefAdminID)
            loggedinIntent.putExtra("deviceID", prefDeviceID)

        }*/

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createURL = "https://osam.riyenas.dev/api/soldier/create"

        return_btn.setOnClickListener {

            var successLogIn = false

            val name = editTextTextPersonName.text.toString()
            val rank = editTextTextPersonServiceNumber.text.toString()
            val unit = editTextTextSignUpCode.text.toString()
            //val signUpCode = editTextTextSignUpCode.text.toString()
            //val tm = getSystemService(Context.TELEPHONY_SERVICE)
            //val serialNumber = Build.getSerial()
            //val phone = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            //val phoneNumber = phone.line1Number.toString()
            //val type = "phone"
            //val manufacturer = phone.manufacturerCode.toString()

            val params = HashMap<String, String>()
            params["name"] = name
            params["rank"] = rank
            params["unit"] = unit
            params["signUpCode"] = "88888888"
            params["manufacturer"] = "manufacturer"
            params["phoneNumber"] = "phoneNumber"
            params["serialNumber"] = "serialNumber"
            params["serviceNumber"] = "serviceNumber"
            params["type"] = "type"

            var seed = ""
            var adminID = ""
            var deviceID = ""

            val userJSON = JSONObject(params as Map<*, *>)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, createURL, userJSON,
                Response.Listener { response ->
                    editor.clear()
                    seed = response.getString("seed")
                    adminID = response.getString("adminID")
                    deviceID = response.getString("deviceID")
                    editor.putString("seed", seed)
                    editor.putString("adminID", adminID)
                    editor.putString("deviceID", deviceID)
                    editor.putString("name", name)
                    editor.putString("rank", rank)
                    editor.putString("unit", unit)
                    editor.apply()
                    /*prefs.setString("seed", seed)
                    prefs.setString("adminID", adminID)
                    prefs.setString("deviceID", deviceID)
                    prefs.setString("name", name)
                    prefs.setString("rank", rank)
                    prefs.setString("unit", unit)*/
                    successLogIn = true
                },
                Response.ErrorListener {
                    Toast.makeText(this, "연결 실패", Toast.LENGTH_SHORT).show()
                    successLogIn = false
                }
            )

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

            if (successLogIn) {

                val loggedInIntent = Intent(this, LoggedInMainActivity::class.java)
                startActivity(loggedInIntent)
                loggedInIntent.putExtra("seed", seed)
                loggedInIntent.putExtra("adminID", adminID)
                loggedInIntent.putExtra("deviceID", deviceID)

            }

            /*val stringRequest = StringRequest(Request.Method.GET, createURL,
            Response.Listener<String> {
                response -> println(response.toString())
            }, Response.ErrorListener { error ->
                    println(error.toString())
                })

            Volley.newRequestQueue(this).add(stringRequest)*/

        }
    }
}