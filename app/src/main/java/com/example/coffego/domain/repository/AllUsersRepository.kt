package com.example.coffego.domain.repository

import com.example.coffego.domain.model.User

interface AllUsersRepository {
    suspend fun getAllUsers(): List<User>
}