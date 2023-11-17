package com.ashiq.elitestore.ui

import androidx.compose.runtime.Composable
import com.ashiq.elitestore.theme.AppTheme
import moe.tlaster.precompose.PreComposeApp
import org.koin.compose.KoinApplication

@Composable
fun App() {
    AppTheme {
        PreComposeApp {
            Navigation()
        }
    }
}