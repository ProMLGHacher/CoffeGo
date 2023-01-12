package com.example.coffego.domain.error

sealed class RequestError : Error() {
    object ConnectionError: RequestError()
    object NoFoundData: RequestError()
    object ErrorServer: RequestError()
    object UnAuthorized: RequestError()
}
