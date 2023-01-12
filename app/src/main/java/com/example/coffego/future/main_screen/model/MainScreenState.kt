package com.example.coffego.future.main_screen.model

import com.example.coffego.data.network.catalog.Product

sealed class MainScreenState {
    object Loading : MainScreenState()
    object ConnectionError : MainScreenState()
    data class Success(
        val data : Map<String, List<Product>> = mapOf()
    ) : MainScreenState()
}
