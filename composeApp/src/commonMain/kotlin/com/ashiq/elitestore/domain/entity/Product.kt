package com.ashiq.elitestore.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val image: String,
    val price: Double,
    val description: String
)
