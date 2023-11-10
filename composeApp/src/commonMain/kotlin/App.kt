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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import domain.Product

val products = listOf(
    Product(
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        price = 109.95,
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
    ),
    Product(
        title = "Mens Casual Premium Slim Fit T-Shirts",
        price = 22.23,
        image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"
    )
)

@Composable
fun App() {
    MaterialTheme {
        Scaffold(
            scaffoldState = rememberScaffoldState(),
            topBar = {
                TopAppBar(title = { Text(text = "Elite Store") })
            }
        ) {
            LazyColumn(Modifier.padding(it)) {
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
    }
}