package com.ahmad.coderstool.ui.components

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onNavigationClick: () -> Unit,
    isConnected: Boolean? = null,
    onStatusButtonClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            if (isConnected != null && onStatusButtonClick != null) {
                Button(
                    onClick = onStatusButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isConnected) Color(0xFF4CAF50) else Color(0xFFF44336),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    Text(if (isConnected) "WiFi Connected" else "WiFi Disconnected")
                }
            }
        }
    )
}
