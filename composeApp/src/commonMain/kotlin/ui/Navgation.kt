package ui

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import ui.details.DetailsScreen
import ui.home.HomeScreen
import util.Constants.APP_NAME

sealed class Route(val route: String) {
    data object Home : Route("$APP_NAME/home")
    data object Details : Route("$APP_NAME/details")
}

@Composable
fun Navigation() {
    val navigator = rememberNavigator()

    NavHost(
        navigator = navigator,
        initialRoute = Route.Home.route
    ) {
        scene(route = Route.Home.route) { HomeScreen() }
        scene(route = Route.Details.route) { DetailsScreen() }
    }
}