package com.example.coffego.data.ustils

import com.example.coffego.data.network.all_users.UserBody
import com.example.coffego.domain.model.User

internal fun UserBody.toUser() : User {
    return User(
        mail = mail ?: "",
        password = password ?: "",
        name_image = name_image ?: "",
        login = login ?: ""
    )
}

internal fun List<UserBody>.toUserList() : List<User> {
    val list = mutableListOf<User>()
    this.forEach {
        list += it.toUser()
    }
    return list
}