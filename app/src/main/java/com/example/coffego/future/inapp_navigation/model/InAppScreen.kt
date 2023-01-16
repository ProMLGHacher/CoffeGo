package com.example.coffego.future.inapp_navigation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class InAppScreen(val route: String, val icon: ImageVector) {
    object Catalog : InAppScreen("catalog", Icons.Default.Home)
    object Cart : InAppScreen("cart", Icons.Default.ShoppingCart)
    object Map : InAppScreen("map", Icons.Default.LocationOn)
}
