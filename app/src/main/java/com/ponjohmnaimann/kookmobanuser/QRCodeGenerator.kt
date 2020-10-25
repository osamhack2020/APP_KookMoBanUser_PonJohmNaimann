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
    val tOTPValue = TOTP.generateTOTP(PrefInit.prefs.seed, steps, "8", "HMacSHA512")

    val qrParams = HashMap<String, String?>()
    qrParams["adminID"] = adminID
    qrParams["deviceID"] = deviceID
    qrParams["TOTP"] = tOTPValue

    val mapper = jacksonObjectMapper()
    val qrContent = mapper.writeValueAsString(qrParams)
    val qrImage = view.findViewById<ImageView>(R.id.qrImage)

    val multiFormatWriter = MultiFormatWriter()
    val bitMatrix: BitMatrix = multiFormatWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300)
    val barcodeEncoder = BarcodeEncoder()
    val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

    qrImage.setImageBitmap(bitmap)

}