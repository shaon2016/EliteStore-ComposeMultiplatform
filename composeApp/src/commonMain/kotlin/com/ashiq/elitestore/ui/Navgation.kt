package com.ashiq.elitestore.ui

import androidx.compose.runtime.Composable
import com.ashiq.elitestore.ui.details.DetailsScreen
import com.ashiq.elitestore.ui.home.HomeContract
import com.ashiq.elitestore.ui.home.HomeScreen
import com.ashiq.elitestore.util.Constants.APP_NAME
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.koinInject

sealed class Route(val route: String) {
    data object Home : Route("$APP_NAME/home")
    data object Details : Route("$APP_NAME/details")
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
                }
            }
        }
        scene(route = Route.Details.route) { entry ->
            DetailsScreen(
                product = sharedViewModel.product,
                navigateBack = { navigator.goBack() }
            )
        }
    }
}

fun encodeToBase64(input: String): String {
    val byteArray = input.toByteArray(Charsets.UTF_8)
    return base64Encode(byteArray)
}

fun decodeFromBase64(input: String): String {
    val byteArray = base64Decode(input)
    return byteArray.decodeToString()
}

fun base64Encode(input: ByteArray): String {
    val base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

    val result = StringBuilder()
    var buffer: Int
    var position = 0

    while (position < input.size) {
        buffer = (input[position++].toInt() and 0xFF) shl 16
        if (position < input.size) buffer = buffer or ((input[position++].toInt() and 0xFF) shl 8)
        if (position < input.size) buffer = buffer or (input[position++].toInt() and 0xFF)

        result.append(base64Chars[(buffer shr 18) and 0x3F])
            .append(base64Chars[(buffer shr 12) and 0x3F])
            .append(if (position < input.size) base64Chars[(buffer shr 6) and 0x3F] else '=')
            .append(if (position < input.size) base64Chars[buffer and 0x3F] else '=')
    }

    return result.toString()
}

fun base64Decode(input: String): ByteArray {
    val base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    val result = mutableListOf<Byte>()

    var buffer: Int
    var position = 0

    while (position < input.length) {
        buffer = (base64Chars.indexOf(input[position++]) and 0x3F) shl 18
        buffer = buffer or ((base64Chars.indexOf(input[position++]) and 0x3F) shl 12)
        buffer = buffer or ((base64Chars.indexOf(input[position++]) and 0x3F) shl 6)
        buffer = buffer or (base64Chars.indexOf(input[position++]) and 0x3F)

        result.add((buffer shr 16).toByte())
        if (position < input.length && input[position] != '=') result.add((buffer shr 8).toByte())
        if (position < input.length && input[position] != '=') result.add(buffer.toByte())
    }

    return result.toByteArray()
}