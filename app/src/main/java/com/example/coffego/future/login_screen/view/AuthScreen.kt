package com.example.coffego.future.login_screen.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffego.R
import com.example.coffego.future.common.LoadingWidget
import com.example.coffego.future.login_screen.model.AuthScreenEvent
import com.example.coffego.future.login_screen.model.AuthScreenState
import com.example.coffego.future.login_screen.view_model.AuthScreenViewModel
import com.example.coffego.future.navigation.model.Screen

@ExperimentalMaterial3Api
@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel,
    navController: NavController
) {
    val state by remember {
        mutableStateOf(viewModel.state)
    }
    when (state.value) {
        is AuthScreenState.Auth -> Auth(
            data = (state.value as AuthScreenState.Auth),
            switchToRegistration = { viewModel.onEvent(AuthScreenEvent.SwitchToRegistration) },
            switchToLogin = {
                viewModel.onEvent(AuthScreenEvent.SwitchToLogin)
            },
            onLoginTextFieldChange = {
                viewModel.onEvent(AuthScreenEvent.OnLoginChange(it))
            },
            onPasswordTextFieldChange = {
                viewModel.onEvent(AuthScreenEvent.OnPasswordChange(it))
            },
            submit = {
                viewModel.onEvent(AuthScreenEvent.Submit)
            },
        )
        AuthScreenState.Loading -> LoadingWidget()
        AuthScreenState.Success -> {}
        AuthScreenState.ConnectionError -> ConErr { viewModel.onEvent(AuthScreenEvent.Submit) }
    }
    LaunchedEffect(key1 = state.value) {
        if (state.value is AuthScreenState.Success) {
            navController.popBackStack()
            navController.navigate(Screen.MainScreen.route)
        }
    }
}

@Composable
fun ConErr(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Oops!", fontSize = 22.sp)
        Text(text = "Кажется какие-то проблемы с сетью =(", fontSize = 14.sp)
        Button(
            onClick = onClick
        ) {
            Text("Повторить попытку")
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Auth(
    data: AuthScreenState.Auth,
    switchToLogin: () -> Unit,
    switchToRegistration: () -> Unit,
    onLoginTextFieldChange: (value: String) -> Unit,
    onPasswordTextFieldChange: (value: String) -> Unit,
    submit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.star),
            contentDescription = "LOGO",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Добро пожаловать!",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            if (data.isLogin) "Авторизация" else "Регистрация",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(45.dp))
        TextField(
            value = data.login,
            onValueChange = {
                onLoginTextFieldChange(it)
            },
            placeholder = {
                Text(text = "Логин")
            },
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .height(50.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.primary.copy(0.2f)),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Start)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = data.password,
            onValueChange = {
                onPasswordTextFieldChange(it)
            },
            placeholder = {
                Text(text = "Пароль")
            },
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .height(50.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.primary.copy(0.2f)),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White, textAlign = TextAlign.Start
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(modifier = Modifier
            .padding(horizontal = 40.dp)
            .width(300.dp)
            .height(50.dp),
            shape = RoundedCornerShape(4.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.onPrimary,
//                contentColor = MaterialTheme.colorScheme.primary
//            ),
            onClick = {
                submit()
            }) {
            Text("Войти")
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(
            modifier = Modifier.height(25.dp),
            contentPadding = PaddingValues(horizontal = 10.dp),
            onClick = {
                if (data.isLogin)
                    switchToRegistration()
                else
                    switchToLogin()
            }) {
            Text(text = "У меня нет аккаунта. Зарегистрироваться!", fontSize = 11.sp)
        }
    }
}