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

            val qrIntent = Intent(this, QRcodeActivity::class.java)
            startActivity(qrIntent)

        }
    }

    override fun onBackPressed() {

        var mBackWait : Long = 0
        if (PrefInit.prefs.successLogIn == true) {
            if (System.currentTimeMillis() - mBackWait >= 2000) {
                mBackWait = System.currentTimeMillis()
                Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                finish()
            }
        }

    }
}