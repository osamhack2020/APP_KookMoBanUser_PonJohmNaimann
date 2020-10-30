package com.ponjohmnaimann.kookmobanuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_logged_in_main.*

class LoggedInMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in_main)

        return_btn.setOnClickListener {

            LoadingDialog(this).show()

            val qrIntent = Intent(this, QRcodeActivity::class.java)
            startActivity(qrIntent)
            LoadingDialog(this).dismiss()

        }
    }

    private var backPressedTime : Long = 0

    override fun onBackPressed() {

        if (PrefInit.prefs.successLogIn) {
            val systemTime = System.currentTimeMillis()
            val timeInterval = systemTime - backPressedTime
            if (timeInterval in 0..2000) {
                moveTaskToBack(true)
            }
            else {
                backPressedTime = systemTime
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}