package com.example.coffego.future.navigation.model

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object AllLogins : Screen("all_logins")
    object MainScreen : Screen("main_screen")
}
