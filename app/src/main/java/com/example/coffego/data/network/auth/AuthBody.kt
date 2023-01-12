package com.example.coffego.data.network.auth

@kotlinx.serialization.Serializable
data class AuthBody(
    val login: String,
    val password: String
)
