package com.example.coffego.future.login_screen.model

sealed class AuthScreenState {
    object Loading : AuthScreenState()
    data class Auth(
        val login : String = "",
        val password : String = "",
        val isLogin : Boolean = true
    ) : AuthScreenState()
    object Success : AuthScreenState()
    object ConnectionError : AuthScreenState()
}
