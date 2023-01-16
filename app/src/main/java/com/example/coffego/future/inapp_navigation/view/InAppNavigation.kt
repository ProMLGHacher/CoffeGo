package com.example.coffego.future.inapp_navigation.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.coffego.future.cart_screen.view.CartScreen
import com.example.coffego.future.cart_screen.view_model.CartScreenViewModel
import com.example.coffego.future.inapp_navigation.model.InAppScreen
import com.example.coffego.future.main_screen.view.MainScreen
import com.example.coffego.future.main_screen.view_model.MainScreenViewModel

val screens = listOf(
    InAppScreen.Catalog,
    InAppScreen.Map,
    InAppScreen.Cart,
)

@ExperimentalGlideComposeApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun InAppNavigation() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            screens.forEach { screen ->
                BottomNavigationItem(selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Icon(screen.icon,
                            contentDescription = "catalog",
                            tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) Color.White else Color.Gray)
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
        }
    }) { innerPadding ->
        NavHost(
            navController,
            startDestination = InAppScreen.Catalog.route,
            Modifier.padding(innerPadding)
        ) {
            composable(InAppScreen.Catalog.route) {
                val viewModel: MainScreenViewModel = hiltViewModel()
                MainScreen(
                    viewModel = viewModel
                )
            }
            composable(InAppScreen.Map.route) {
                val viewModel: MainScreenViewModel = hiltViewModel()
                MainScreen(
                    viewModel = viewModel
                )
            }
            composable(InAppScreen.Cart.route) {
                val viewModel: CartScreenViewModel = hiltViewModel()
                CartScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}