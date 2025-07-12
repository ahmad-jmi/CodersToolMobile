package com.example.coderstool.ui.screens

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.coderstool.network.isWifiConnected
import kotlinx.coroutines.delay

@Composable
fun SetupScreen(
) {
    val context = LocalContext.current
    var isConnected by remember { mutableStateOf(false) }

    // 🟢 Poll every 5 seconds to check Wi-Fi status
    LaunchedEffect(Unit) {
        while (true) {
            val status = isWifiConnected(context)
            if (isConnected != status) {
                isConnected = status
            }
            delay(5000L) // check every 5 seconds
        }
    }

    SetupContent()
}

@Composable
private fun SetupContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        // No body content right now
    }
}
