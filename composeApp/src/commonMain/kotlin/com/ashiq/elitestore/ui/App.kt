package com.ashiq.elitestore.ui

import androidx.compose.runtime.Composable
import com.ashiq.elitestore.di.MyKoinApplication
import moe.tlaster.precompose.PreComposeApp
import com.ashiq.elitestore.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        PreComposeApp {
            MyKoinApplication {
                Navigation()
            }
        }
    }
}