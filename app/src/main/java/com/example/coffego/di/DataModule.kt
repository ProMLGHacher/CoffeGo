package com.example.coffego.di

import com.example.coffego.data.network.all_users.AllUsersNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideAllUserNetwork(httpClient: HttpClient) : AllUsersNetwork {
        return AllUsersNetwork(
            httpClient = httpClient
        )
    }

}