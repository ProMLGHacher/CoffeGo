package com.example.coffego.data.repository

import android.util.Log
import com.example.coffego.data.network.all_users.AllUsersNetwork
import com.example.coffego.data.ustils.toUserList
import com.example.coffego.domain.error.RequestError
import com.example.coffego.domain.model.User
import com.example.coffego.domain.repository.AllUsersRepository
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import javax.inject.Inject

class AllUsersRepositoryImpl @Inject constructor(
    private val allUsersNetwork: AllUsersNetwork
) : AllUsersRepository {
    override suspend fun getAllUsers(): List<User> {
        try {
            val response = allUsersNetwork.get()
            return response.toUserList()
        } catch (e: IOException){
            throw RequestError.ConnectionError
        } catch (e: ClientRequestException) {
            Log.e(TAG, e.message)
            throw RequestError.ConnectionError
        }
    }

    companion object {
        const val TAG = "AllUsersRepositoryImpl::"
    }
}