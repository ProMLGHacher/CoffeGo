package com.example.coffego.domain.model
import kotlinx.serialization.Serializable

data class ItemFromCatalog(
    val item: String,
    val price: Int,
    var icon_name: String = "unknown.png",
    val item_description: String = "No description"
)