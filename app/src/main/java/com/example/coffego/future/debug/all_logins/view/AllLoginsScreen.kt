package com.example.coffego.future.debug.all_logins.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffego.domain.model.User
import com.example.coffego.future.common.LoadingWidget
import com.example.coffego.future.debug.all_logins.model.AllUsersScreenEvent
import com.example.coffego.future.debug.all_logins.model.AllUsersState
import com.example.coffego.future.debug.all_logins.view_model.AllLoginsScreenViewModel

@Composable
fun AllLoginsScreen(
    viewModel : AllLoginsScreenViewModel
) {
    val state by remember {
        viewModel.state
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when(state) {
            AllUsersState.ConnectionError -> ConErr {
                viewModel.onEvent(
                    AllUsersScreenEvent.LoadUsers
                )
            }
            AllUsersState.Loading -> LoadingWidget()
            AllUsersState.Static -> St {
                run {
                    viewModel.onEvent(
                        AllUsersScreenEvent.LoadUsers
                    )
                }
            }
            is AllUsersState.Success -> Success(
                (state as AllUsersState.Success).userList
            ) {
                viewModel.onEvent(
                    AllUsersScreenEvent.LoadUsers
                )
            }
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

@Composable
fun St(
    onClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = onClick
        ) {
            Text("НАЖИМАЙ")
        }
    }
}

@Composable
fun Success(
    userList: List<User>,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(userList) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center) {
                        Text(it.login)
                        Text(it.password)
                        Text(it.mail.toString())
                        Text(it.name_image.toString())
                    }
                }
            }
            item {
                Box(modifier = Modifier.fillMaxSize()) {
                    Button(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = onClick
                    ) {
                        Text("ОБНОВИТЬ")
                    }
                }
            }
        }
    }
}