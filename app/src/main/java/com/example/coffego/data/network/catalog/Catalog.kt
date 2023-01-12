package com.example.coffego.data.network.catalog

import kotlinx.serialization.Serializable

@Serializable
data class Catalog(
    val coffee: List<Product>,
    val tea: List<Product>
)