package com.example.coffego.future.common

interface EventBase<T> {
    fun onEvent(event:T)
}