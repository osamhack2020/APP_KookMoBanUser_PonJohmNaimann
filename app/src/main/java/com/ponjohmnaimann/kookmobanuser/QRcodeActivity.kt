package com.ponjohmnaimann.kookmobanuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.coroutines.*

class QRcodeActivity : AppCompatActivity() {

    var backButtonPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_rcode)

        val deviceID = PrefInit.prefs.deviceID
        val adminID = PrefInit.prefs.adminID
        val isReturnSuccess = PrefInit.prefs.returnSuccess
        val returnCheckURL = "https://osam.riyenas.dev/api/log/find/device/$deviceID/last"

        val context = this
        val view = findViewById<View>(R.id.qrCodeActivity)
        backButtonPressed = false

        GlobalScope.launch(Dispatchers.Main) {
            while (isReturnSuccess != "PASS") {
                qrCodeGenerator(view, deviceID, adminID)
                // 나중에는 delay를 줄여도 됨
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            while (isReturnSuccess != "PASS") {
                returnSuccessCheck(context, view, returnCheckURL)
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
            }
        }

}

    override fun onBackPressed() {
        backButtonPressed = true
        val logInIntent = Intent(this, LoggedInMainActivity::class.java)
        startActivity(logInIntent)
    }

}