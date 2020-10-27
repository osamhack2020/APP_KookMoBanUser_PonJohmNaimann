package com.ponjohmnaimann.kookmobanuser


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
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

        runBlocking {

           val qrGenerateJob = GlobalScope.launch(Dispatchers.Main) {
                while (isReturnSuccess != "PASS") {
                    qrCodeGenerator(context, view, deviceID, adminID )
                    // 나중에는 delay를 줄여도 됨
                    delay(10000L)
                    if (backButtonPressed) {
                        break
                    }
                }
            }
            val returnCheckJob = GlobalScope.launch(Dispatchers.IO) {
                while (isReturnSuccess != "PASS") {
                    returnSuccessCheck(context, view, returnCheckURL)
                    delay(10000L)
                    if (backButtonPressed) {
                        break
                    }
                }
            }

            qrGenerateJob.join()
            returnCheckJob.join()

        }
    }

    override fun onBackPressed() {
        backButtonPressed = true
        val logInIntent = Intent(this, LoggedInMainActivity::class.java)
        startActivity(logInIntent)
    }
}