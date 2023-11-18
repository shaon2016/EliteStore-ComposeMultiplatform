package com.ashiq.elitestore.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashiq.elitestore.domain.entity.CartItem
import com.ashiq.elitestore.ui.component.LAUNCH_LISTEN_FOR_EFFECTS
import com.ashiq.elitestore.util.roundTo
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.ahsiq.eliteStore.sharingResources.SharedRes
import org.koin.compose.koinInject

@Composable
internal fun CartScreen(onNavigationRequest: (CartContract.Effect.Navigation) -> Unit) {
    val viewModel: CartViewModel = koinInject()
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        viewModel.setEvent(CartContract.Event.Initialize)

        viewModel.effect.onEach { effect ->
            when (effect) {
                is CartContract.Effect.Navigation -> onNavigationRequest(effect)
            }
        }.collect()
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.setEvent(CartContract.Event.NavigateBack) },
                        content = {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    )
                },
                title = { Text(text = "Cart") }
            )
        }
    ) { paddingValues ->
        CartContent(
            modifier = Modifier.padding(paddingValues),
            cartItems = state.cartItems,
            total = state.total,
            onEventSent = { viewModel.setEvent(it) }
        )

        if (state.showOrderDialog) {
            OrderDialog(
                onEventSent = { viewModel.setEvent(CartContract.Event.HideOrderDialog) }
            )
        }
    }
}

@Composable
private fun OrderDialog(onEventSent: (CartContract.Event) -> Unit) {
    AlertDialog(
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(SharedRes.strings.order_dialog_msg),
                    style = MaterialTheme.typography.h5
                )
            }
        },
        backgroundColor = Color.LightGray,
        onDismissRequest = {},
        confirmButton = {
            TextButton(
                onClick = {
                    onEventSent(CartContract.Event.HideOrderDialog)
                }
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.h5
                )
            }
        }
    )
}

@Composable
private fun CartContent(
    modifier: Modifier,
    cartItems: List<CartItem>,
    total: Double,
    onEventSent: (CartContract.Event) -> Unit
) {
    Column(modifier = modifier.then(Modifier.fillMaxSize())) {
        LazyColumn(Modifier.weight(1f).fillMaxWidth()) {
            items(
                items = cartItems,
                key = { it.productId }
            ) { item ->
                CartItemRow(
                    cartItem = item,
                    onEventSent = onEventSent
                )

                Spacer(Modifier.height(1.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.LightGray)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(SharedRes.strings.total),
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium)
                )
                Text(
                    text = "${total.roundTo()}",
                    style = MaterialTheme.typography.h6.copy(fontSize = 18.sp)
                )
            }

            Button(
                onClick = {
                    onEventSent(CartContract.Event.PlaceOrder)
                }
            ) {
                Text(text = stringResource(SharedRes.strings.place_order))
            }
        }
    }
}

@Composable
private fun CartItemRow(
    cartItem: CartItem,
    onEventSent: (CartContract.Event) -> Unit
) {
    Card(elevation = 4.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    start = 8.dp,
                    bottom = 8.dp
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .height(80.dp),
                painter = rememberImagePainter(cartItem.imageUrl),
                contentDescription = null
            )

            Column(Modifier.padding(start = 8.dp).weight(1f).fillMaxHeight()) {
                Text(
                    text = cartItem.title,
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = cartItem.price.toString(),
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            if (cartItem.quantity > 1) {
                                onEventSent(
                                    CartContract.Event.Decrement(
                                        cartItem.productId,
                                        cartItem.quantity - 1
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(SharedRes.images.ic_subtract),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = cartItem.quantity.toString()
                    )
                    IconButton(
                        onClick = {
                            if (cartItem.quantity < 99) {
                                onEventSent(
                                    CartContract.Event.Increment(
                                        cartItem.productId,
                                        cartItem.quantity + 1
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }

            IconButton(
                onClick = {
                    onEventSent(CartContract.Event.Remove(cartItem.productId))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}




