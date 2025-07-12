package com.example.coderstool.utils

import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Collections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NetworkScanner {

    suspend fun getLocalIPs(): List<String> = withContext(Dispatchers.IO) {
        val ips = mutableListOf<String>()
        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress && addr is InetAddress) {
                        val ip = addr.hostAddress ?: continue
                        if (ip.contains(".")) ips.add(ip)
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return@withContext ips
    }
}
