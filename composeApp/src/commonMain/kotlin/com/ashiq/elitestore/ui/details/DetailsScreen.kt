package com.ashiq.elitestore.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import com.ashiq.elitestore.domain.entity.Product

@Composable
fun DetailsScreen(
    product: Product,
    navigateBack: () -> Unit
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                        content = {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    )
                },
                title = { Text(text = "Details") }
            )
        }
    ) {
        Column {
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
    }
}