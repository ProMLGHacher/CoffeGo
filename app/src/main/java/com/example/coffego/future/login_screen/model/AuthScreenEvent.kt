package com.example.coffego.future.login_screen.model

sealed class AuthScreenEvent {
    object SwitchToLogin : AuthScreenEvent()
    object SwitchToRegistration : AuthScreenEvent()
    class OnLoginChange(val login: String) : AuthScreenEvent()
    class OnPasswordChange(val password: String) : AuthScreenEvent()
    object Submit : AuthScreenEvent()
}
