package com.example.coderstool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.coderstool.ui.MainScreen
import com.example.coderstool.ui.theme.CodersToolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodersToolTheme {
                MainScreen()
            }
        }
    }
}
