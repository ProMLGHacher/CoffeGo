package com.example.coffego.future.debug.all_logins.model

import com.example.coffego.domain.model.User

sealed class AllUsersState{
    object Loading : AllUsersState()
    object ConnectionError : AllUsersState()
    object Static : AllUsersState()
    class Success(val userList: List<User>) : AllUsersState()
}
