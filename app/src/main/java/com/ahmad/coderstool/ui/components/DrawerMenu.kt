package com.ahmad.coderstool.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerMenu(current: String, onSelect: (String) -> Unit) {
    ModalDrawerSheet {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Divider()

        val menuItems = listOf("Home", "Setup", "Logs", "About")
        menuItems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item) },
                selected = item == current,
                onClick = { onSelect(item) },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}
