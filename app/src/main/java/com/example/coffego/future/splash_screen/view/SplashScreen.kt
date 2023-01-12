package com.example.coffego.future.splash_screen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffego.R
import com.example.coffego.future.navigation.model.Screen
import com.example.coffego.future.splash_screen.model.SplashScreenState
import com.example.coffego.future.splash_screen.view_model.SplashScreenViewModel

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel,
    navController: NavController
) {
    val state by remember {
        mutableStateOf(viewModel.state)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.logo),
            contentDescription = "LOGO",
            modifier = Modifier.size(200.dp)
        )
        Text(text = "CoffeeGo", style = MaterialTheme.typography.headlineLarge, fontSize = 70.sp)
    }
    LaunchedEffect(key1 = state.value) {
        when(state.value) {
            is SplashScreenState.Success -> {
                navController.popBackStack()
                navController.navigate(Screen.MainScreen.route)
            }
            SplashScreenState.Loading -> {}
            SplashScreenState.Auth -> {
                navController.popBackStack()
                navController.navigate(Screen.LoginScreen.route)
            }
        }
    }
}