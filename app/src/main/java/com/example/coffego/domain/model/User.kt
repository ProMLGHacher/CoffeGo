package com.example.coffego.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    var password: String,
    var mail: String? = "",
    var name_image: String? = "profile.png"
)

@Serializable
data class TokenUser(
    val login: String?,
    var token: String?,
    var mail: String? = "",
    var name_image: String? = "profile.png"
)


@Serializable
data class OpenUserInformation(
    val login: String, var mail: String?
)

@Serializable
data class UserAuthorization(
    val login: String, val password: String
)



