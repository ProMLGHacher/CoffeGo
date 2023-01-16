package com.example.coffego.future.cart_screen.model

import com.example.coffego.domain.model.CartModel

data class CartScreenState(
    val cart: List<CartModel> = emptyList(),
    val sum: Int = 0
)