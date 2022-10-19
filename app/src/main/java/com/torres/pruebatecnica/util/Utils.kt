package com.torres.pruebatecnica.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun isConnect(context: Context): Boolean {
        var connected = false
        val connec = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val redes = connec.allNetworkInfo
        for (i in redes.indices) {
            if (redes[i].state == NetworkInfo.State.CONNECTED) {
                connected = true
            }
        }
        return connected
    }

    fun formatedDate(date: String): String {
        var todayDate = ""
        try {
            val outputFormat: DateFormat =
                SimpleDateFormat("yyyy")
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val newdate = inputFormat.parse(date)
            todayDate = outputFormat.format(newdate)
        } catch (e: Exception) {
        }
        return todayDate
    }
}