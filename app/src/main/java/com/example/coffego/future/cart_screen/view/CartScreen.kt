package com.example.coffego.future.cart_screen.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.coffego.future.cart_screen.view_model.CartScreenViewModel

@Composable
fun CartScreen(
    viewModel: CartScreenViewModel
) {
    val state by remember {
        mutableStateOf(viewModel.state)
    }
    Text(
        state.value.sum.toString(),
        modifier = Modifier.fillMaxSize()
    )
}