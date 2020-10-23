package com.ponjohmnaimann.kookmobanuser

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.*
import kotlin.collections.HashMap

fun qrCodeGenerator(context : Context, view : View, deviceID: String?, adminID: String?) {

    val time = Calendar.getInstance()
    val steps = TOTP.calcSteps(time.timeInMillis / 1000, 0L, 10L)
    val qrContent = TOTP.generateTOTP(PrefInit.prefs.seed, steps, "8", "HMacSHA512")

    val qrParams = HashMap<String, String?>()
    qrParams["deviceID"] = deviceID
    qrParams["adminID"] = adminID
    qrParams["TOTP"] = qrContent

    val mapper = jacksonObjectMapper()
    val jsonStr = mapper.writeValueAsString(qrParams)

    val multiFormatWriter = MultiFormatWriter()
    val bitMatrix: BitMatrix =
        multiFormatWriter.encode(jsonStr, BarcodeFormat.QR_CODE, 300, 300)
    val barcodeEncoder = BarcodeEncoder()
    val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
    val qrImage = view.findViewById<ImageView>(R.id.qrImage)

    qrImage.setImageBitmap(bitmap)
    Toast.makeText(context, qrContent, Toast.LENGTH_SHORT).show()
}