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

        /*
        GlobalScope.launch(Dispatchers.Main) {
            while (isReturnSuccess != "PASS") {
                val qrImage = view.findViewById<ImageView>(R.id.qrImage)
                qrImage.setImageResource(R.drawable.original)
                Toast.makeText(this@QRcodeActivity, "original", Toast.LENGTH_SHORT).show()
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
                qrImage.setImageResource(R.drawable.coloredqrcode1)
                Toast.makeText(this@QRcodeActivity, "-0.15860554", Toast.LENGTH_SHORT).show()
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
                qrImage.setImageResource(R.drawable.coloredqrcode2)
                Toast.makeText(this@QRcodeActivity, "-0.18651678", Toast.LENGTH_SHORT).show()
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
                qrImage.setImageResource(R.drawable.coloredqrcode3)
                Toast.makeText(this@QRcodeActivity, "-0.60442924", Toast.LENGTH_SHORT).show()
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
                qrImage.setImageResource(R.drawable.coloredqrcode4)
                Toast.makeText(this@QRcodeActivity, "0.05362656", Toast.LENGTH_SHORT).show()
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
                qrImage.setImageResource(R.drawable.coloredqrcode5)
                Toast.makeText(this@QRcodeActivity, "0.7043279", Toast.LENGTH_SHORT).show()
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
                qrImage.setImageResource(R.drawable.coloredqrcode6)
                Toast.makeText(this@QRcodeActivity, "0.90929794", Toast.LENGTH_SHORT).show()
                delay(10000L)
                if (backButtonPressed) {
                    break
                }
            }
        }*/

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