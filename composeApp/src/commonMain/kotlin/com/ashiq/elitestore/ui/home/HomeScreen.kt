package com.ashiq.elitestore.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ashiq.elitestore.domain.entity.Product
import com.ashiq.elitestore.ui.component.LAUNCH_LISTEN_FOR_EFFECTS
import com.ashiq.elitestore.util.UIState
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.ahsiq.eliteStore.sharingResources.SharedRes
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinInject(),
    onNavigationRequested: (navigationEffect: HomeContract.Effect.Navigation) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        viewModel.setEvent(HomeContract.Event.Initialize)

        viewModel.effect.onEach { effect ->
            when (effect) {
                is HomeContract.Effect.Navigation -> onNavigationRequested(effect)
            }
        }.collect()
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(title = { Text(text = stringResource(SharedRes.strings.app_name)) })
        }
    ) { paddingValues ->
        Box(Modifier.fillMaxSize().padding(paddingValues)) {
            when (state.UIState) {
                UIState.INITIAL -> {}
                UIState.SUCCESS -> {
                    HomeContent(
                        modifier = Modifier.padding(paddingValues),
                        products = state.products,
                        onEventSent = { viewModel.setEvent(it) }
                    )
                }

                UIState.FAILED -> {
                    Text("Failed to fetch product list")
                }
            }

            if (state.isLoading)
                CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    products: List<Product>,
    onEventSent: (HomeContract.Event) -> Unit
) {
    LazyColumn(modifier) {
        items(products) { product ->
            Column(Modifier.clickable { onEventSent(HomeContract.Event.ToDetails(product)) }) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 8.dp),
                    painter = rememberImagePainter(product.image),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 2.dp)
                        .padding(horizontal = 16.dp),
                    text = product.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2
                )

                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 16.dp),
                    text = "Price: ${product.price}",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1
                )
            }
        }
    }
}
