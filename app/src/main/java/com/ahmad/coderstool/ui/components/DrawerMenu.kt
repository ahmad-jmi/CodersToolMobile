package com.ahmad.coderstool.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerMenu(current: String, onSelect: (String) -> Unit) {
    ModalDrawerSheet {
        Text("Menu", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        Divider()
        listOf("Home", "Setup", "About").forEach {
            NavigationDrawerItem(
                label = { Text(it) },
                selected = it == current,
                onClick = { onSelect(it) }
            )
        }
    }
}
