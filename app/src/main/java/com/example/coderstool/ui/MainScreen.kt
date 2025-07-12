package com.example.coderstool.ui

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.coderstool.network.isWifiConnected
import com.example.coderstool.ui.components.DrawerMenu
import com.example.coderstool.ui.components.TopBar
import com.example.coderstool.ui.screens.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val screenHistory = remember { mutableStateListOf("Home") }
    val currentScreen = screenHistory.last()

    var isConnected by remember { mutableStateOf(false) }

    LaunchedEffect(currentScreen) {
        if (currentScreen == "Setup") {
            while (true) {
                isConnected = isWifiConnected(context)
                delay(5000L)
            }
        }
    }

    BackHandler(enabled = screenHistory.size > 1) {
        screenHistory.removeAt(screenHistory.size - 1)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu(currentScreen, onSelect = {
                if (it != currentScreen) screenHistory.add(it)
                scope.launch { drawerState.close() }
            })
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = currentScreen,
                    onNavigationClick = { scope.launch { drawerState.open() } },
                    isConnected = if (currentScreen == "Setup") isConnected else null,
                    onStatusButtonClick = if (currentScreen == "Setup") {
                        { openWifiSettings(context) }
                    } else null
                )
            }
        ) { padding ->
            Box(modifier = Modifier
                .padding(padding)
                .fillMaxSize()
            ) {
                when (currentScreen) {
                    "Home" -> HomeScreen(onItemClick = {
                        if (it != currentScreen) screenHistory.add(it)
                    })
                    "Setup" -> SetupScreen()
                    "About" -> AboutScreen()
                    else -> FeatureScreen(currentScreen)
                }
            }
        }
    }
}

private fun openWifiSettings(context: Context) {
    context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    })
}
