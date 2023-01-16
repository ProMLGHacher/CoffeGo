package com.example.coffego.data.storage.cart

class CartStorage {

    private val _cart: MutableList<CartData> = mutableListOf()

    fun add(item: CartData) {
        repeat(_cart.size) {
            if (_cart[it].name == item.name) {
                _cart[it].count ++
                return
            }
        }
        _cart.add(item)
    }

    fun getCart() : List<CartData> {
        return _cart
    }

    fun removeCartItem(item: CartData) {
        repeat(_cart.size) {
            if (_cart[it].name == item.name) {
                _cart[it].count --
                return
            }
        }
        _cart.remove(item)
    }

    fun clearCart() {
        _cart.clear()
    }

}