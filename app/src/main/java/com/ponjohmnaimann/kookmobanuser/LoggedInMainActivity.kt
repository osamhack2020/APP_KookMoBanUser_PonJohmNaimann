package com.ponjohmnaimann.kookmobanuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_logged_in_main.*

class LoggedInMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in_main)

        return_btn.setOnClickListener {

            val loadingText = findViewById<TextView>(R.id.loadingText)
            loadingText.text = "QR코드 생성중"
            LoadingDialog(this).show()

            val qrIntent = Intent(this, QRcodeActivity::class.java)
            startActivity(qrIntent)
            LoadingDialog(this).dismiss()

        }
    }

    var backbtnPressedTime : Long = 0

    override fun onBackPressed() {
        if (PrefInit.prefs.successLogIn == true) {
            val systemTime = System.currentTimeMillis()
            val timeInterval = systemTime - backbtnPressedTime
            if (timeInterval in 0..2000) {
                moveTaskToBack(true)
            }
            else {
                backbtnPressedTime = systemTime
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}