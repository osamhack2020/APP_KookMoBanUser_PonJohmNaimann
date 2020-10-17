package com.ponjohmnaimann.kookmobanuser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
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

            //val tm = getSystemService(Context.TELEPHONY_SERVICE)
            //val serialNumber = Build.getSerial()
            //val phone = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            //val phoneNumber = phone.line1Number.toString()
            //val type = "phone"
            //val manufacturer = phone.manufacturerCode.toString()

            val params = HashMap<String, String>()
            params["name"] = name
            params["rank"] = "rank"
            params["unit"] = "unit"
            params["signUpCode"] = "88888888"
            params["manufacturer"] = "manufacturer"
            params["phoneNumber"] = "phoneNumber"
            params["serialNumber"] = "serialNumber"
            params["serviceNumber"] = serviceNumber
            params["type"] = "type"

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
            }
            else {
                Toast.makeText(this, "로그인 실패" +
                        "\n"+ "이름: " + PrefInit.prefs.name.toString() +
                        "\n"+ "seed: " + PrefInit.prefs.seed.toString(), Toast.LENGTH_SHORT).show()
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