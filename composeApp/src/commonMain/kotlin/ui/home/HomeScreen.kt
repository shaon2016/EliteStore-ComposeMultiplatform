package ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import com.seiko.imageloader.rememberImagePainter
import domain.entity.Product
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject
import ui.DataState
import ui.LAUNCH_LISTEN_FOR_EFFECTS

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinInject()) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        viewModel.setEvent(HomeContract.Event.Initialize)

        viewModel.effect.onEach { effect ->
            when (effect) {
                else -> {

                }
            }
        }.collect()
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(title = { Text(text = "Elite Store") })
        }
    ) { paddingValues ->
        Box(Modifier.fillMaxSize().padding(paddingValues)) {
            when (state.dataState) {
                DataState.INITIAL -> {}
                DataState.SUCCESS -> {
                    HomeContent(
                        modifier = Modifier.padding(paddingValues),
                        products = state.products
                    )
                }

                DataState.FAILED -> {
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
    products: List<Product>
) {
    LazyColumn(modifier) {
        items(products) { product ->
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
