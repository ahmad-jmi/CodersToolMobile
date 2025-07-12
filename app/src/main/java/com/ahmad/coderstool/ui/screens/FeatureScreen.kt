package com.ahmad.coderstool.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

@Composable
fun FeatureScreen(featureName: String) {
    Text("This is a $featureName page", modifier = Modifier.padding(16.dp))
}
