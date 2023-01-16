package com.example.coffego.data.ustils

import com.example.coffego.data.storage.cart.CartData
import com.example.coffego.domain.model.CartModel
import com.example.coffego.domain.model.User

internal fun CartData.toCartModel() : CartModel {
    return CartModel(
        name,
        price,
        count
    )
}

internal fun List<CartData>.toCartModelList() : List<CartModel> {
    val list = mutableListOf<CartModel>()
    this.forEach {
        list += it.toCartModel()
    }
    return list
}