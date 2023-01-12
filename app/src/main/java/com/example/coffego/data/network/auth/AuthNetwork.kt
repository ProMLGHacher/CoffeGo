package com.example.coffego.data.network.auth


import android.util.Log
import com.example.coffego.data.network.all_users.AllUsersNetwork
import com.example.coffego.domain.error.RequestError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class AuthNetwork @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun auth(
        authBody: AuthBody
    ) : ResponseUser {
        return httpClient.post("/login", ) {
            contentType(ContentType.Application.Json)
            setBody(authBody)
        }.body()
    }
}