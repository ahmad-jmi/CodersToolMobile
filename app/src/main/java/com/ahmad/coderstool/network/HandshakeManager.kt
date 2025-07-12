package com.ahmad.coderstool.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL

suspend fun scanLocalNetworkAndHandshake(context: Context): Boolean = withContext(Dispatchers.IO) {
    val baseIp = getDeviceBaseIp() ?: return@withContext false

    for (i in 1..254) {
        val targetIp = "$baseIp.$i"
        val isSuccess = sendHandshake(targetIp)
        if (isSuccess) {
            Log.d("Handshake", "Handshake accepted by $targetIp")
            // You can store this IP as backend server in preferences or database
            return@withContext true
        }
    }
    false
}

private fun getDeviceBaseIp(): String? {
    try {
        val ip = InetAddress.getLocalHost().hostAddress ?: return null
        val parts = ip.split(".")
        return if (parts.size == 4) "${parts[0]}.${parts[1]}.${parts[2]}" else null
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

private fun sendHandshake(ip: String): Boolean {
    return try {
        val url = URL("http://$ip:8080/handshake/")
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "application/json")
        conn.connectTimeout = 500
        conn.readTimeout = 500
        conn.doOutput = true

        val json = JSONObject()
        json.put("message", "requesting handshake for connection")
        json.put("deviceIP", InetAddress.getLocalHost().hostAddress)

        OutputStreamWriter(conn.outputStream).use { it.write(json.toString()) }

        val responseCode = conn.responseCode
        conn.disconnect()

        responseCode in 200..299
    } catch (e: Exception) {
        false
    }
}
