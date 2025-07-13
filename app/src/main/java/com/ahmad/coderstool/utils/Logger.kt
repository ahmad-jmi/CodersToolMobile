package com.ahmad.coderstool.utils

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object Logger {
    private const val FILE_NAME = "app_log.txt"

    fun log(context: Context, message: String) {
        val logMessage = "[${getCurrentTimestamp()}] $message\n"
        try {
            val file = File(context.filesDir, FILE_NAME)
            val writer = FileWriter(file, true)
            writer.append(logMessage)
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readLogs(context: Context): String {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            file.readText()
        } catch (e: Exception) {
            "No logs found or error reading log file."
        }
    }

    fun clearLogs(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (file.exists()) {
            file.delete()
        }
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
