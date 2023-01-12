package com.example.coffego.di

import com.example.coffego.data.network.all_users.AllUsersNetwork
import com.example.coffego.data.network.auth.AuthNetwork
import com.example.coffego.data.network.catalog.CatalogNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAllUserNetwork(httpClient: HttpClient) : AllUsersNetwork {
        return AllUsersNetwork(
            httpClient = httpClient
        )
    }

    @Provides
    @Singleton
    fun provideAuthNetwork(httpClient: HttpClient) : AuthNetwork {
        return AuthNetwork(
            httpClient = httpClient
        )
    }

    @Provides
    @Singleton
    fun provideCatalogNetwork(httpClient: HttpClient) : CatalogNetwork {
        return CatalogNetwork(
            httpClient = httpClient
        )
    }

}