package com.ponjohmnaimann.kookmobanuser

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        if (PrefInit.prefs.successLogIn) {

            val loggedinIntent = Intent(this, LoggedInMainActivity::class.java)
            startActivity(loggedinIntent)

        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PrefInit.prefs.successLogIn = false

        val createURL = "https://osam.riyenas.dev/api/soldier/create"

        return_btn.setOnClickListener {

            LoadingDialog(this).show()

            val name = editTextTextPersonName.text.toString()
            val serviceNumber = editTextTextPersonServiceNumber.text.toString()
            val signUpCode = editTextTextSignUpCode.text.toString()
            val manufacturer = Build.MANUFACTURER
            val guid = UUID.randomUUID().toString()

            val userParams = HashMap<String, String>()
            userParams["name"] = name
            userParams["signUpCode"] = signUpCode
            userParams["manufacturer"] = manufacturer
            userParams["guid"] = guid
            userParams["serviceNumber"] = serviceNumber
            val userJSON = JSONObject(userParams as Map<*, *>)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, createURL, userJSON,
                Response.Listener { response ->
                    PrefInit.prefs.seed = response.getString("seed")
                    PrefInit.prefs.adminID = response.getLong("adminId").toString()
                    PrefInit.prefs.deviceID = response.getLong("deviceId").toString()
                    PrefInit.prefs.successLogIn = true
                    PrefInit.prefs.name = name
                    PrefInit.prefs.serviceNumber = serviceNumber
                    PrefInit.prefs.signUpCode = signUpCode
                },
                Response.ErrorListener {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            )
            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

            if (PrefInit.prefs.successLogIn) {
                val returnCheckRequest = JsonObjectRequest(
                    Request.Method.GET,
                    "https://osam.riyenas.devapi/admin/find/signUpCode/${PrefInit.prefs.signUpCode}",
                    null,
                    Response.Listener { response ->
                        val adminName = response.getString("name")
                        Toast.makeText(this, "관리자 : $adminName", Toast.LENGTH_SHORT).show()
                        val loggedInIntent = Intent(this, LoggedInMainActivity::class.java)
                        startActivity(loggedInIntent)
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "잘못된 초대 코드입니다.", Toast.LENGTH_SHORT).show()
                    }
                )
                Volley.newRequestQueue(this).add(returnCheckRequest)
            }

            LoadingDialog(this).dismiss()
        }
    }
}