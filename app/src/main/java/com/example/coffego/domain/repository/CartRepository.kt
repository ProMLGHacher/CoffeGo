package com.example.coffego.domain.repository

import com.example.coffego.domain.model.CartModel
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun add(item: CartModel)
    suspend fun getCart() : Flow<List<CartModel>>
    suspend fun removeCartItem(item: CartModel)
    suspend fun clearCart()
}