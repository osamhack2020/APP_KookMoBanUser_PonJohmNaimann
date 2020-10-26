package com.ponjohmnaimann.kookmobanuser

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ColorSpace
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.red
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.*
import java.nio.IntBuffer
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

    runBlocking {
        val qrEncoder = GlobalScope.launch(Dispatchers.Main) {

            var pixelsArray = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(pixelsArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

            for (y in 0 until pixelsArray.size) {
                if (pixelsArray[y] != -1) {

                    val red = 255
                    val green = Random().nextInt(256)
                    val blue = Random().nextInt(256)
                    val newColorForBlack = Color.rgb(red, green, blue)
                    pixelsArray[y] = newColorForBlack

                }
                else {

                    val red = 0
                    val green = Random().nextInt(256)
                    val blue = Random().nextInt(256)
                    val newColorForWhite = Color.rgb(red, green, blue)
                    pixelsArray[y] = newColorForWhite

                }
            }

            qrImage.setImageBitmap(bitmap)
            /*
            for (i in 0..bitmap.width) {
                for (j in 0..bitmap.height) {
                    var red = Color.red(bitmap.getPixel(i, j))
                    //var green = Color.green(bitmap.getPixel(i, j))
                    //var blue = Color.blue(bitmap.getPixel(i, j))
                    var green = Random().nextInt(256)
                    var blue = Random().nextInt(256)
                    val rgb = Color.rgb(red, green, blue)
                    bitmap.setPixel(i, j, rgb)
                }
            }*/

        }
        qrEncoder.join()
    }


}