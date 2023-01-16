package com.example.coffego.future.cart_screen.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffego.domain.repository.CartRepository
import com.example.coffego.future.cart_screen.model.CartScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _state = mutableStateOf(CartScreenState())
    val state by ::_state

    init {
        getCart()
    }

    private fun getCart() {
        viewModelScope.launch {
            cartRepository.getCart().onEach {
                var cartSum = 0
                it.forEach { cartItem ->
                    cartSum += cartItem.count * cartItem.price
                }
                _state.value = CartScreenState(
                    cart = it,
                    sum = cartSum
                )
            }.launchIn(this)
        }
    }

}