package com.example.coffego.future.splash_screen.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffego.data.storage.user_storage.UserStorage
import com.example.coffego.future.splash_screen.model.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userStorage: UserStorage
) : ViewModel() {

    private val _state = mutableStateOf<SplashScreenState>(SplashScreenState.Loading)
    val state: State<SplashScreenState> by ::_state

    init {
        viewModelScope.launch {
            delay(1200)
            Log.e("LIKFSJILSKUODAHFNKLSDHNFLKUSD", userStorage.getToken().toString())
            if (userStorage.getToken() != null) {
                _state.value = SplashScreenState.Success
            } else {
                _state.value = SplashScreenState.Auth
            }
        }
    }

}