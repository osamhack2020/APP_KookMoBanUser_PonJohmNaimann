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

        prefs = PreferenceUtil(applicationContext)
        val loginName = prefs.getString("name", null)
        val loginRank = prefs.getString("rank", null)
        val loginUnit = prefs.getString("unit", null)
        val prefSeed = prefs.getString("seed", null)
        val prefAdminID = prefs.getString("adminID", null)
        val prefUserID = prefs.getString("userID", null)

        if (loginName != null && loginRank != null && loginUnit != null
            && prefSeed != null && prefAdminID != null && prefUserID != null) {

            val loggedinIntent = Intent(this, LoggedInMainActivity::class.java)
            startActivity(loggedinIntent)

        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createURL = "https://osam.riyenas.dev/api/soldier/create"

        return_btn.setOnClickListener {

            var successLogIn = false

            val name = editTextTextPersonName.text.toString()
            val rank = editTextTextPersonRank.text.toString()
            val unit = editTextTextPersonUnit.text.toString()
            val signUpCode = editTextTextSignUpCode.text.toString()
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
            params["signUpCode"] = signUpCode
            params["manufacturer"] = "manufacturer"
            params["phoneNumber"] = "phoneNumber"
            params["serialNumber"] = "serialNumber"
            params["serviceNumber"] = "serviceNumber"
            params["type"] = "type"

            var seed = ""
            var adminID = ""
            var userID = ""

            val userJSON = JSONObject(params as Map<*, *>)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, createURL, userJSON,
                Response.Listener { response ->
                    seed = response.getString("seed")
                    adminID = response.getString("adminID")
                    userID = response.getString("deviceID")
                    prefs.setString("seed", seed)
                    prefs.setString("adminID", adminID)
                    prefs.setString("userID", userID)
                    prefs.setString("name", name)
                    prefs.setString("rank", rank)
                    prefs.setString("unit", unit)
                    successLogIn = true
                },
                Response.ErrorListener {
                    Toast.makeText(this, "연결 실패", Toast.LENGTH_SHORT).show()
                    successLogIn = false
                }
            )

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

            if (successLogIn) {
                val qrIntent = Intent(this, QRcodeActivity::class.java)
                startActivity(qrIntent)
                qrIntent.putExtra("seed", seed)
                qrIntent.putExtra("adminID", adminID)
                qrIntent.putExtra("userID", userID)
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