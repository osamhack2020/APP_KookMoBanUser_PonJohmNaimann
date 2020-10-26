package com.ponjohmnaimann.kookmobanuser

import android.content.Context
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.logging.Handler

fun returnSuccessCheck(context: Context, view : View, returnCheckURL : String) {

    val returnCheckRequest = StringRequest(Request.Method.GET, returnCheckURL,
        Response.Listener<String> { response ->
            PrefInit.prefs.returnSuccess = response.toString()
        }, Response.ErrorListener { _ ->
            Toast.makeText(context, "반납 미완료!", Toast.LENGTH_SHORT).show()
        })
    Volley.newRequestQueue(context).add(returnCheckRequest)

}
