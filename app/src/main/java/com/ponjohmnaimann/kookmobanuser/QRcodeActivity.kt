package com.ponjohmnaimann.kookmobanuser

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_q_rcode.*
import java.util.*

class QRcodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_rcode)

        val seed = intent.getStringExtra("seed")
        val adminID = intent.getStringExtra("adminID")
        val userID = intent.getStringExtra("userID")

        val time = Calendar.getInstance()
        val steps = TOTP.calcSteps(time.timeInMillis / 1000, 0L, 10L)
        val qr_contents = TOTP.generateTOTP(seed, steps, "8", "HMacSHA512")

        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix: BitMatrix = multiFormatWriter.encode(qr_contents.toString(), BarcodeFormat.QR_CODE, 300, 300)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

        qr_image.setImageBitmap(bitmap)

    }
}