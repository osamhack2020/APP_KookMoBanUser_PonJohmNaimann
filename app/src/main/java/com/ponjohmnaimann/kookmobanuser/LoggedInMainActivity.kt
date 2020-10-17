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

        Toast.makeText(this, "name: "+PrefInit.prefs.name.toString()
                +"\n"+ "seed: "+PrefInit.prefs.seed.toString()
                +"\n"+ "adminID: "+PrefInit.prefs.adminID.toString()
                +"\n"+ "deviceID: "+PrefInit.prefs.deviceID.toString(), Toast.LENGTH_SHORT).show()

        return_btn.setOnClickListener {

            val qrIntent = Intent(this, QRcodeActivity::class.java)
            startActivity(qrIntent)

        }
    }
}