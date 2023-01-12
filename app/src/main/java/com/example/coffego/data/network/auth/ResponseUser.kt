package com.example.coffego.data.network.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResponseUser(
    var login: String? = null,
    var token: String? = null,
    var mail:String? = null,
    var name_icon: String? = null
)
