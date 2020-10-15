package com.ponjohmnaimann.kookmobanuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_logged_in_main.*

class LoggedInMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in_main)

        return_btn.setOnClickListener {

            val qrIntent = Intent(this, QRcodeActivity::class.java)
            startActivity(qrIntent)

        }
    }
}