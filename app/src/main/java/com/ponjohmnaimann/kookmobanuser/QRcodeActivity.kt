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

class QRcodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_rcode)

        val time = Calendar.getInstance()
        val steps = TOTP.calcSteps(time.timeInMillis / 1000, 0L, 10L)

        val tOTPValue = TOTP.generateTOTP(PrefInit.prefs.seed, steps, "8", "HMacSHA512")

        val qrParams = HashMap<String, String?>()
        qrParams["deviceID"] = PrefInit.prefs.deviceID
        qrParams["adminID"] = PrefInit.prefs.adminID
        qrParams["TOTP"] = tOTPValue

        val mapper = jacksonObjectMapper()
        val qrContent = mapper.writeValueAsString(qrParams)

        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix: BitMatrix = multiFormatWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

        qr_image.setImageBitmap(bitmap)
        Toast.makeText(this, "deviceID : ${PrefInit.prefs.deviceID}\nTOTP : $tOTPValue", Toast.LENGTH_SHORT).show()

    }
}

