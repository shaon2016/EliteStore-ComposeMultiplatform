package com.ashiq.elitestore.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ashiq.elitestore.domain.entity.Product
import com.seiko.imageloader.rememberImagePainter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject

@Composable
fun DetailsScreen(
    product: Product,
    onNavigationRequest: (DetailsContract.Effect.Navigation) -> Unit
) {
    val viewModel: DetailsViewModel = koinInject()

    LaunchedEffect(Unit) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is DetailsContract.Effect.Navigation -> onNavigationRequest(effect)
            }
        }.collect()
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.setEvent(DetailsContract.Event.NavigateBack) },
                        content = {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    )
                },
                title = { Text(text = "Details") },
                actions = {
                    IconButton(
                        onClick = { viewModel.setEvent(DetailsContract.Event.ToCartScreen) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    )
                }
            )
        }
    ) {
        Column(Modifier.padding(it).fillMaxSize()) {
            Column(Modifier.fillMaxWidth().weight(1f)) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(250.dp),
                    painter = rememberImagePainter(product.image),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
                    text = product.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2
                )

                Text(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .padding(horizontal = 16.dp),
                    text = "Price: ${product.price}",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
                    text = "Description",
                    style = MaterialTheme.typography.h5,
                    maxLines = 1
                )

                Text(
                    modifier = Modifier
                        .padding(top = 1.dp)
                        .padding(horizontal = 16.dp),
                    text = product.description,
                    style = MaterialTheme.typography.subtitle1
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                onClick = {
                    viewModel.setEvent(DetailsContract.Event.AddToCart(product))
                }
            ) {
                Text("Add to cart")
            }
        }
    }
}