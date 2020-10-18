package com.ponjohmnaimann.kookmobanuser

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_q_rcode.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class QRcodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_rcode)

        val time = Calendar.getInstance()
        val steps = TOTP.calcSteps(time.timeInMillis / 1000, 0L, 10L)

        val qrContent = TOTP.generateTOTP(PrefInit.prefs.seed, steps, "8", "HMacSHA512")

        val qrParams = HashMap<String, String?>()
        qrParams["deviceID"] = PrefInit.prefs.deviceID
        qrParams["adminID"] = PrefInit.prefs.adminID
        qrParams["TOTP"] = qrContent

        val mapper = jacksonObjectMapper()
        val jsonStr = mapper.writeValueAsString(qrParams)

        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix: BitMatrix = multiFormatWriter.encode(jsonStr, BarcodeFormat.QR_CODE, 300, 300)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

        qr_image.setImageBitmap(bitmap)

    }
}