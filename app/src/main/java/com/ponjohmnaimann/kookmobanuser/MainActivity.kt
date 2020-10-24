package com.ponjohmnaimann.kookmobanuser

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        if (PrefInit.prefs.successLogIn) {

            val loggedinIntent = Intent(this, LoggedInMainActivity::class.java)
            startActivity(loggedinIntent)

        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PrefInit.prefs.successLogIn = false
        editTextTextPersonName.hint = PrefInit.prefs.name
        editTextTextPersonServiceNumber.hint = PrefInit.prefs.serviceNumber
        editTextTextSignUpCode.hint = PrefInit.prefs.signUpCode

        val createURL = "https://osam.riyenas.dev/api/soldier/create"

        return_btn.setOnClickListener {

            val name = editTextTextPersonName.text.toString()
            val serviceNumber = editTextTextPersonServiceNumber.text.toString()
            val signUpCode = editTextTextSignUpCode.text.toString()
            val manufacturer = Build.MANUFACTURER
            val serialNumber = Build.SERIAL
            val phone = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var phoneNumber : String = ""
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "휴대폰 번호를 불러올 수 없습니다!", Toast.LENGTH_SHORT).show()
            }
            else {
                phoneNumber = phone.line1Number.toString()
            }


            val params = HashMap<String, String>()
            params["name"] = name
            params["signUpCode"] = signUpCode
            params["manufacturer"] = manufacturer
            params["phoneNumber"] = phoneNumber
            params["serialNumber"] = serialNumber
            params["serviceNumber"] = serviceNumber

            val userJSON = JSONObject(params as Map<*, *>)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, createURL, userJSON,
                Response.Listener { response ->
                    PrefInit.prefs.seed = response.getString("seed")
                    PrefInit.prefs.adminID = response.getString("adminId")
                    PrefInit.prefs.deviceID = response.getString("deviceId")
                    PrefInit.prefs.successLogIn = true
                    PrefInit.prefs.name = name
                    PrefInit.prefs.serviceNumber = serviceNumber
                },
                Response.ErrorListener {
                    Toast.makeText(this, "연결 실패", Toast.LENGTH_SHORT).show()
                }
            )

            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

            if (PrefInit.prefs.successLogIn) {
                val loggedInIntent = Intent(this, LoggedInMainActivity::class.java)
                startActivity(loggedInIntent)
                Toast.makeText(this, "manufacturer: $manufacturer" + "\n" +
                    "phoneNumber: $phoneNumber" + "\n" +
                    "serialNumber : $serialNumber", Toast.LENGTH_SHORT).show()
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