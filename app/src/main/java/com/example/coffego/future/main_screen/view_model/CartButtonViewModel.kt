package com.example.coffego.future.main_screen.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffego.domain.model.CartModel
import com.example.coffego.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartButtonViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private var _state = mutableStateOf(0)
    val state: State<Int> by ::_state

    init {
        getCartCount()
    }

    private fun getCartCount() {
        viewModelScope.launch {
            cartRepository.getCart()
                .onEach {
               withContext(Dispatchers.Main) {
                   it.forEach { item ->
                       _state.value = item.count
                   }
               }
            }.stateIn(this)
        }
    }

}