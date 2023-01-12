package com.example.coffego.data.repository

import android.util.Log
import com.example.coffego.data.network.all_users.AllUsersNetwork
import com.example.coffego.data.network.auth.AuthBody
import com.example.coffego.data.network.auth.AuthNetwork
import com.example.coffego.data.storage.user_storage.User
import com.example.coffego.data.storage.user_storage.UserStorage
import com.example.coffego.domain.error.RequestError
import com.example.coffego.domain.model.TokenUser
import com.example.coffego.domain.model.UserAuthorization
import com.example.coffego.domain.repository.AuthRepository
import io.ktor.client.plugins.*
import io.ktor.http.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userStorage: UserStorage,
    private val authNetwork: AuthNetwork
) : AuthRepository {
    override suspend fun auth(data: UserAuthorization) : TokenUser {
        try {
            val response = authNetwork.auth(
                authBody = AuthBody(
                    data.login,
                    data.password
                )
            )
            userStorage.saveUser(
                User(
                    login = response.login,
                    token = response.token,
                    mail = response.mail,
                    nameIcon = response.name_icon
                )
            )
            Log.e("LLL", userStorage.getToken().toString())
            return TokenUser(
                login = response.login,
                token = response.token,
                mail = response.mail,
                name_image = response.name_icon
            )
        }
        catch (e: ClientRequestException) {
            when(e.response.status){
                HttpStatusCode.Unauthorized -> {
                    userStorage.saveUser(User())
                    throw RequestError.UnAuthorized
                }
                else -> throw e
            }
        }
        catch (e: Exception) {
            Log.e(AllUsersNetwork.TAG, e.toString())
            throw RequestError.ConnectionError
        }
    }
}