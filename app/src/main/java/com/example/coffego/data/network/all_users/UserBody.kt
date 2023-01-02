package com.example.coffego.data.network.all_users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBody(
    @SerialName("login")
    val login:String?,
    @SerialName("password")
    var password: String?,
    @SerialName("mail")
    var mail: String?,
    @SerialName("name_image")
    var name_image: String?
)