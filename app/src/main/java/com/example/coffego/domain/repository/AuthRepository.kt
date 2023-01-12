package com.example.coffego.domain.repository

import com.example.coffego.domain.model.TokenUser
import com.example.coffego.domain.model.UserAuthorization

interface AuthRepository {
    suspend fun auth(data: UserAuthorization) : TokenUser
}