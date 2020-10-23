package com.ponjohmnaimann.kookmobanuser

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_q_rcode.*
import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.*
import kotlin.collections.HashMap

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