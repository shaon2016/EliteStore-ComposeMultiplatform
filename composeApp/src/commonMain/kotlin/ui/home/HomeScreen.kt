package ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import data.network.util.onSuccess
import domain.entity.Product
import domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun HomeScreen() {
    //    val viewModel : HomeViewModel = koinInject()
    val products = mutableStateOf<List<Product>>(listOf())
    val repo: ProductRepository = koinInject()

    LaunchedEffect("First") {
        val response = repo.getProducts()
        response.onSuccess {
            products.value = it
        }
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(title = { Text(text = "Elite Store") })
        }
    ) {
        LazyColumn(Modifier.padding(it)) {
            items(products.value) { product ->
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