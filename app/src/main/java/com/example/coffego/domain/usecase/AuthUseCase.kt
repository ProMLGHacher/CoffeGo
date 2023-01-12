package com.example.coffego.domain.usecase

import com.example.coffego.domain.model.TokenUser
import com.example.coffego.domain.model.UserAuthorization
import com.example.coffego.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(value: UserAuthorization): TokenUser {
        return authRepository.auth(value)
    }
}