package com.ahmad.coderstool.ui

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ahmad.coderstool.ui.screens.HomeScreen
import com.ahmad.coderstool.network.isWifiConnected
import com.ahmad.coderstool.ui.components.DrawerMenu
import com.ahmad.coderstool.ui.components.TopBar
import com.ahmad.coderstool.ui.screens.AboutScreen
import com.ahmad.coderstool.ui.screens.LogsScreen
import com.ahmad.coderstool.ui.screens.FeatureScreen
import com.ahmad.coderstool.ui.screens.SetupScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(startOnScreen: String = "Home") {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // 👇 Start with the screen passed as a parameter
    val screenHistory = remember { mutableStateListOf(startOnScreen) }
    val currentScreen = screenHistory.last()

    var isConnected by remember { mutableStateOf(false) }

    // Poll for Wi-Fi status only on Setup screen
    LaunchedEffect(currentScreen) {
        if (currentScreen == "Setup") {
            while (true) {
                isConnected = isWifiConnected(context)
                delay(5000L)
            }
        }
    }

    // Back press navigates up in the screen stack
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
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                when (currentScreen) {
                    "Home" -> HomeScreen(onItemClick = {
                        if (it != currentScreen) screenHistory.add(it)
                    })
                    "Setup" -> SetupScreen()
                    "Logs" -> LogsScreen()
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
