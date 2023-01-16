package com.example.coffego.future.main_screen.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffego.data.network.catalog.CatalogNetwork
import com.example.coffego.domain.error.RequestError
import com.example.coffego.future.main_screen.model.MainScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val catalogNetwork: CatalogNetwork
) : ViewModel() {

    private var _state = mutableStateOf<MainScreenState>(MainScreenState.Loading)
    val state: State<MainScreenState> by ::_state

    init {
        getCatalog()
    }

    fun getCatalog() {
        viewModelScope.launch {
            delay(500)
            runCatching {
                catalogNetwork.getAll()
            }.onSuccess {
                _state.value = MainScreenState.Success(it)
            }.onFailure(::onError)
        }
    }

    private fun onError(throwable: Throwable) {
        when (throwable) {
            RequestError.ConnectionError -> _state.value = MainScreenState.ConnectionError
            else -> Log.e(
                "MainScreenViewModel", throwable.message.toString()
            )
        }
    }

}