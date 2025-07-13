package com.ahmad.coderstool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.ahmad.coderstool.network.isWifiConnected
import com.ahmad.coderstool.ui.MainScreen
import com.ahmad.coderstool.network.scanLocalNetworkAndHandshake

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            var wifiConnected by remember { mutableStateOf(false) }
            var showWifiDialog by remember { mutableStateOf(false) }
            var showBackendDownDialog by remember { mutableStateOf(false) }
            var startOnSetup by remember { mutableStateOf(false) }
            var showMain by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                wifiConnected = isWifiConnected(context)

                if (!wifiConnected) {
                    showWifiDialog = true
                } else {
                    val success = scanLocalNetworkAndHandshake(context)
                    if (!success) {
                        showBackendDownDialog = true
                    }
                    showMain = true
                }
            }

            when {
                showWifiDialog -> {
                    AlertDialog(
                        onDismissRequest = {},
                        confirmButton = {
                            TextButton(onClick = {
                                showWifiDialog = false
                                startOnSetup = true
                                showMain = true
                            }) {
                                Text("Go To Setup")
                            }
                        },
                        title = { Text("Wi-Fi not connected") },
                        text = { Text("Please connect to Wi-Fi to proceed.") }
                    )
                }

                showBackendDownDialog -> {
                    AlertDialog(
                        onDismissRequest = {},
                        confirmButton = {
                            TextButton(onClick = {
                                showBackendDownDialog = false
                                showMain = true
                            }) {
                                Text("OK")
                            }
                        },
                        title = { Text("Backend Server is Down") },
                        text = { Text("No device responded to handshake.") }
                    )
                }

                showMain -> {
                    MainScreen(startOnScreen = if (startOnSetup) "Setup" else "Home")
                }
            }
        }
    }
}
