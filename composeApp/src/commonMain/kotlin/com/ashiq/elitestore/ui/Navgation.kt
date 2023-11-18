package com.ashiq.elitestore.ui

import androidx.compose.runtime.Composable
import com.ashiq.elitestore.ui.cart.CartContract
import com.ashiq.elitestore.ui.cart.CartScreen
import com.ashiq.elitestore.ui.details.DetailsContract
import com.ashiq.elitestore.ui.details.DetailsScreen
import com.ashiq.elitestore.ui.home.HomeContract
import com.ashiq.elitestore.ui.home.HomeScreen
import com.ashiq.elitestore.util.Constants.APP_NAME
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.koinInject

sealed class Route(val route: String) {
    data object Home : Route("$APP_NAME/home")
    data object Details : Route("$APP_NAME/details")
    data object Cart : Route("$APP_NAME/cart")
}

@Composable
fun Navigation() {
    val navigator = rememberNavigator()
    val sharedViewModel: SharedViewModel = koinInject()

    NavHost(
        navigator = navigator,
        initialRoute = Route.Home.route
    ) {
        scene(route = Route.Home.route) {
            HomeScreen { navigationEffect ->
                when (navigationEffect) {
                    is HomeContract.Effect.Navigation.ToDetails -> {
                        sharedViewModel.product = navigationEffect.product
                        navigator.navigate(Route.Details.route)
                    }

                    is HomeContract.Effect.Navigation.ToCartScreen -> {
                        navigator.navigate(Route.Cart.route)
                    }
                }
            }
        }
        scene(route = Route.Details.route) {
            DetailsScreen(product = sharedViewModel.product) { onNavigationRequested ->
                when (onNavigationRequested) {
                    is DetailsContract.Effect.Navigation.NavigateBack -> navigator.goBack()
                    is DetailsContract.Effect.Navigation.ToCartScreen -> {
                        navigator.navigate(Route.Cart.route)
                    }
                }
            }
        }

        scene(route = Route.Cart.route) {
            CartScreen { onNavigationRequested ->
                when (onNavigationRequested) {
                    is CartContract.Effect.Navigation.NavigateBack -> navigator.goBack()
                    is CartContract.Effect.Navigation.NavigateBackToHome -> {
                        navigator.navigate(
                            Route.Home.route,
                            NavOptions(
                                popUpTo = PopUpTo(
                                    // The destination of popUpTo
                                    route = Route.Home.route,
                                    // Whether the popUpTo destination should be popped from the back stack.
                                    inclusive = true,
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}