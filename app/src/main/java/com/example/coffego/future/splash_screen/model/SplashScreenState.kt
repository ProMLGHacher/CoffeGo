package com.example.coffego.future.splash_screen.model

sealed class SplashScreenState {
    object Loading : SplashScreenState()
    object Success : SplashScreenState()
    object Auth : SplashScreenState()
}
