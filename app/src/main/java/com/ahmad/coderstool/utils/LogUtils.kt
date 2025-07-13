package com.ahmad.coderstool.util

import android.content.Context
import java.io.File

object LogUtils {
    private const val LOG_FILE_NAME = "coders_tool_logs.txt"

    fun appendLog(context: Context, message: String) {
        try {
            val file = File(context.filesDir, LOG_FILE_NAME)
            file.appendText("$message\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readLogs(context: Context): String {
        return try {
            val file = File(context.filesDir, LOG_FILE_NAME)
            if (file.exists()) file.readText() else ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun clearLogs(context: Context) {
        try {
            val file = File(context.filesDir, LOG_FILE_NAME)
            if (file.exists()) file.writeText("")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
