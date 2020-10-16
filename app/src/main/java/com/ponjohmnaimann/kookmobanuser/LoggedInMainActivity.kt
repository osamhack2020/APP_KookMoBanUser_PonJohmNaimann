package com.ponjohmnaimann.kookmobanuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_logged_in_main.*

class LoggedInMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in_main)

        val seed = intent.getStringExtra("seed")
        val adminID = intent.getStringExtra("adminID")
        val deviceID = intent.getStringExtra("deviceID")

        return_btn.setOnClickListener {

            val qrIntent = Intent(this, QRcodeActivity::class.java)
            startActivity(qrIntent)
            qrIntent.putExtra("seed", seed)
            qrIntent.putExtra("adminID", adminID)
            qrIntent.putExtra("deviceID", deviceID)

        }
    }
}