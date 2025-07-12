package com.example.coderstool.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.coderstool.ui.components.DrawerMenu
import com.example.coderstool.ui.components.TopBar
import com.example.coderstool.ui.screens.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // History stack of screen names
    val screenHistory = remember { mutableStateListOf("Home") }
    val currentScreen = screenHistory.last()

    // Handle back press
    BackHandler(enabled = screenHistory.size > 1) {
        screenHistory.removeAt(screenHistory.size - 1)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu(currentScreen, onSelect = {
                if (it != currentScreen) {
                    screenHistory.add(it)
                }
                scope.launch { drawerState.close() }
            })
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = currentScreen,
                    onNavigationClick = { scope.launch { drawerState.open() } }
                )
            }
        ) { padding ->
            Box(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {

                when (currentScreen) {
                    "Home" -> HomeScreen(onItemClick = {
                        if (it != currentScreen) {
                            screenHistory.add(it)
                        }
                    })
                    "Setup" -> SetupScreen()
                    "About" -> AboutScreen()
                    else -> FeatureScreen(currentScreen)
                }
            }
        }
    }
}
