package com.example.coffego.data.repository

import com.example.coffego.data.storage.cart.CartData
import com.example.coffego.data.storage.cart.CartStorage
import com.example.coffego.data.ustils.toCartModelList
import com.example.coffego.domain.model.CartModel
import com.example.coffego.domain.repository.CartRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartStorage: CartStorage
) : CartRepository {

    private val cartFlow = MutableStateFlow(cartStorage.getCart().toCartModelList())

    override suspend fun add(item: CartModel) {
        item.apply {
            cartStorage.add(
                CartData(
                    name = name,
                    price = price,
                    count = count
                )
            )
        }
        cartFlow.emit(cartStorage.getCart().toCartModelList())
    }

    override suspend fun getCart(): Flow<List<CartModel>> = cartFlow.asSharedFlow().buffer(1)

    override suspend fun removeCartItem(item: CartModel) {
        item.apply {
            cartStorage.removeCartItem(
                CartData(
                    name = name,
                    price = price,
                    count = count
                )
            )
        }
        cartFlow.emit(cartStorage.getCart().toCartModelList())
    }

    override suspend fun clearCart() {
        cartStorage.clearCart()
        cartFlow.emit(cartStorage.getCart().toCartModelList())
    }
}