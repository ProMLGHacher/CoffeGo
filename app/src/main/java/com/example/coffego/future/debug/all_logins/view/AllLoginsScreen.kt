package com.example.coffego.future.debug.all_logins.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coffego.domain.model.User
import com.example.coffego.future.debug.all_logins.model.AllUsersScreenEvent
import com.example.coffego.future.debug.all_logins.model.AllUsersState
import com.example.coffego.future.debug.all_logins.view_model.AllLoginsScreenViewModel

@Composable
fun AllLoginsScreen(
    viewModel : AllLoginsScreenViewModel = hiltViewModel()
) {
    val state by remember {
        viewModel.state
    }
    when(state) {
        AllUsersState.ConnectionError -> ConErr()
        AllUsersState.Loading -> Lo()
        AllUsersState.Static -> St()
        is AllUsersState.Success -> Success(
            (state as AllUsersState.Success).userList
        )
    }
    Button(onClick = {
        viewModel.onEvent(
            AllUsersScreenEvent.LoadUsers
        )
    }) {
        Text("НАЖИМАЙ")
    }
}

@Composable
fun ConErr() {
    Text("conErr")
}

@Composable
fun Lo() {
    Text("Loading")
}

@Composable
fun St() {
    Text("Static")
}

@Composable
fun Success(
    userList: List<User>
) {
    Text(userList.toString())
}