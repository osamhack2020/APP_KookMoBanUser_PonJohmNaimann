package com.ponjohmnaimann.kookmobanuser

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceData(context: Context) {

    val prefsFileName = "prefs"
    val prefKeySeed = "seed"
    val prefKeyAdminID = "adminID"
    val prefKeyDeviceID = "deviceID"
    val prefKeyName = "name"
    val prefKeyServiceNumber = "serviceNumber"
    val prefKeySignUpCode = "signUpCode"
    val prefKeySuccessLogIn = "successLogIn"

    private val prefs : SharedPreferences = context.getSharedPreferences(prefsFileName, 0)

    var seed: String?
        get () = prefs.getString(prefKeySeed, null)
        set(value) = prefs.edit().putString(prefKeySeed, value).apply()

    var adminID: String?
        get () = prefs.getString(prefKeyAdminID, null)
        set(value) = prefs.edit().putString(prefKeyAdminID, value).apply()

    var deviceID: String?
        get () = prefs.getString(prefKeyDeviceID, null)
        set(value) = prefs.edit().putString(prefKeyDeviceID, value).apply()

    var name: String?
        get () = prefs.getString(prefKeyName, "이름을 입력하세요")
        set(value) = prefs.edit().putString(prefKeyName, value).apply()

    var serviceNumber: String?
        get () = prefs.getString(prefKeyServiceNumber, "군번을 입력하세요")
        set(value) = prefs.edit().putString(prefKeyServiceNumber, value).apply()

    var signUpCode: String?
        get () = prefs.getString(prefKeySignUpCode, "초대 코드를 입력하세요")
        set(value) = prefs.edit().putString(prefKeySignUpCode, value).apply()

    var successLogIn: Boolean
        get () = prefs.getBoolean(prefKeySuccessLogIn, false)
        set(value) = prefs.edit().putBoolean(prefKeySuccessLogIn, value).apply()

}