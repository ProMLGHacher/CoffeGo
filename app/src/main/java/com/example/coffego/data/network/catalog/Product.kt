package com.example.coffego.data.network.catalog

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val icon_name: String,
    val item: String,
    val item_description: String,
    val price: Int
)