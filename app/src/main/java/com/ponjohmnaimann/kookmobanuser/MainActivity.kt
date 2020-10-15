package com.ponjohmnaimann.kookmobanuser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val login_name = prefs.getString("name", null)
        val login_rank = prefs.getString("rank", null)
        val login_unit = prefs.getString("unit", null)

        if (login_name != null && login_rank != null && login_unit != null) {

            val loggedinIntent = Intent(this, LoggedInMainActivity::class.java)
            startActivity(loggedinIntent)

        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createURL = "http://osam-api.herokuapp.com/api/soldier/create"

        return_btn.setOnClickListener {

            val name = editTextTextPersonName.text.toString()
            val rank = editTextTextPersonRank.text.toString()
            val unit = editTextTextPersonUnit.text.toString()
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
            params["manufacturer"] = "manufacturer"
            params["phoneNumber"] = "phoneNumber"
            params["serialNumber"] = "serialNumber"
            params["serviceNumber"] = "serviceNumber"
            params["type"] = "type"
            val userJSON = JSONObject(params as Map<*, *>)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, createURL, userJSON,
                Response.Listener { response -> println("Success") },
                Response.ErrorListener { error -> println("Error : $error") })

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

            val qrIntent = Intent(this, QRcodeActivity::class.java)
            startActivity(qrIntent)

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