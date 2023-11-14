package data.mapper

import data.network.productlist.ProductResponseItem
import domain.entity.Product

fun ProductResponseItem.toDomain() = Product(
    title = title,
    image = image,
    price = price
)