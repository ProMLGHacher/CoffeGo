package com.example.coffego.future.navigation.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.coffego.future.debug.all_logins.view.AllLoginsScreen
import com.example.coffego.future.debug.all_logins.view_model.AllLoginsScreenViewModel
import com.example.coffego.future.inapp_navigation.view.InAppNavigation
import com.example.coffego.future.login_screen.view.AuthScreen
import com.example.coffego.future.login_screen.view_model.AuthScreenViewModel
import com.example.coffego.future.navigation.AppViewModel
import com.example.coffego.future.navigation.model.Screen
import com.example.coffego.future.splash_screen.view.SplashScreen
import com.example.coffego.future.splash_screen.view_model.SplashScreenViewModel

@ExperimentalAnimationApi
@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun NavigationApp(
    viewModel: AppViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            val splashScreenViewModel: SplashScreenViewModel = hiltViewModel()
            SplashScreen(
                viewModel = splashScreenViewModel,
                navController = navController
            )
        }
        composable(Screen.MainScreen.route) {
            InAppNavigation()
        }
        composable(Screen.LoginScreen.route) {
            val authScreenViewModel: AuthScreenViewModel = hiltViewModel()
            AuthScreen(
                viewModel = authScreenViewModel,
                navController
            )
        }
        composable(Screen.AllLogins.route) {
            val allLoginsScreenViewModel: AllLoginsScreenViewModel = hiltViewModel()
            AllLoginsScreen(
                viewModel = allLoginsScreenViewModel
            )
        }
    }
}