package ui

import androidx.compose.runtime.Composable
import di.MyKoinApplication
import moe.tlaster.precompose.PreComposeApp
import theme.AppTheme

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