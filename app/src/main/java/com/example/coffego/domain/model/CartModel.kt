package com.example.coffego.domain.model

data class CartModel(
    val name: String,
    val price: Int,
    val count: Int = 1
)