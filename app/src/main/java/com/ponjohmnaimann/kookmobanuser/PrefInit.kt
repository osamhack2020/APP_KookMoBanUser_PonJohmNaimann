package com.ponjohmnaimann.kookmobanuser

import android.app.Application

class PrefInit : Application() {

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        prefs = SharedPreferenceData(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var prefs : SharedPreferenceData
        lateinit var INSTANCE : PrefInit
    }

}