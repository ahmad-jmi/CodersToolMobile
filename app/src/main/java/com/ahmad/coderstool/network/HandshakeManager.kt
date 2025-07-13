package com.ahmad.coderstool.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ahmad.coderstool.util.LogUtils
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume

suspend fun scanLocalNetworkAndHandshake(context: Context): Boolean {
    val backendIp = "192.168.1.19"
    val backendPort = 8090
    val handshakeUrl = "http://$backendIp:$backendPort/server/handshake"

    LogUtils.appendLog(context, "🔍 Attempting handshake with $handshakeUrl")

    return sendHandshakeRequest(context, handshakeUrl)
}

private suspend fun sendHandshakeRequest(context: Context, url: String): Boolean =
    suspendCancellableCoroutine { continuation ->
        val requestQueue = Volley.newRequestQueue(context)

        val deviceIp = getLocalIpAddress() ?: "unknown"
        val requestBody = JSONObject().apply {
            put("message", "requesting handshake for connection")
            put("deviceIP", deviceIp)
        }

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            requestBody,
            { response ->
                LogUtils.appendLog(context, "✅ Handshake successful: $response")
                continuation.resume(true)
            },
            { error ->
                LogUtils.appendLog(context, "❌ Handshake failed: ${error.message}")
                continuation.resume(false)
            }
        )

        requestQueue.add(request)
    }

fun getLocalIpAddress(): String? {
    return try {
        val interfaces = java.net.NetworkInterface.getNetworkInterfaces()
        for (intf in interfaces) {
            val addresses = intf.inetAddresses
            for (addr in addresses) {
                if (!addr.isLoopbackAddress && addr is java.net.Inet4Address) {
                    return addr.hostAddress
                }
            }
        }
        null
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}
