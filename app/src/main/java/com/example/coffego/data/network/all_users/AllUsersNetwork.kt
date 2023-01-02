package com.example.coffego.data.network.all_users

import android.util.Log
import com.example.coffego.data.repository.AllUsersRepositoryImpl
import com.example.coffego.domain.error.RequestError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import javax.inject.Inject

class AllUsersNetwork @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun get() : List<UserBody> {
        try {
            return httpClient.get(URL).body()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            throw RequestError.ConnectionError
        }
    }

    companion object {
        const val TAG = "AllUsersNetwork::"
        const val URL = "/allLogins"
    }
}