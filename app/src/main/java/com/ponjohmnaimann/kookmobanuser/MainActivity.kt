package com.ponjohmnaimann.kookmobanuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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

        }
    }
}