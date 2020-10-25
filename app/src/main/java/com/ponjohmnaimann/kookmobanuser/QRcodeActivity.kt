package com.ponjohmnaimann.kookmobanuser


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.*

class QRcodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_rcode)

        val deviceID = PrefInit.prefs.deviceID
        val adminID = PrefInit.prefs.adminID
        val isReturnSuccess = PrefInit.prefs.returnSuccess
        val returnCheckURL = "https://osam.riyenas.dev/api/log/find/device/$deviceID/last"

        val context = this
        val view = findViewById<View>(R.id.qrCodeActivity)

        GlobalScope.launch(Dispatchers.IO) {
            while (isReturnSuccess != "PASS") {
                returnSuccessCheck(context, returnCheckURL)
                delay(10000L)
            }
            cancel()
        }

        GlobalScope.launch(Dispatchers.Main) {
            while (isReturnSuccess != "PASS") {
                qrCodeGenerator(context, view, adminID, deviceID)
                delay(10000L)
            }
            cancel()
        }

    }
}