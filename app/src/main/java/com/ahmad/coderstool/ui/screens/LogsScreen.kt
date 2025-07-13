package com.ahmad.coderstool.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ahmad.coderstool.util.LogUtils

@Composable
fun LogsScreen() {
    val context = LocalContext.current
    var logs by remember { mutableStateOf(LogUtils.readLogs(context)) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                LogUtils.clearLogs(context)
                logs = ""
            }) {
                Text("Clear Logs")
            }

            Button(onClick = {
                copyToClipboard(context, logs)
            }) {
                Text("Copy All")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Logs:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        SelectionContainer {
            Text(
                text = logs.ifEmpty { "No logs yet." },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Logs", text)
    clipboard.setPrimaryClip(clip)
}
