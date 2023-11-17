package com.ashiq.elitestore.data.mapper

import com.ashiq.elitestore.data.network.productlist.ProductResponseItem
import com.ashiq.elitestore.domain.entity.Product

fun ProductResponseItem.toDomain() = Product(
    id = id,
    title = title,
    image = image,
    price = price,
    description = description
)