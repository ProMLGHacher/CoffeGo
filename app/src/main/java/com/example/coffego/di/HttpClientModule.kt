package com.example.coffego.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    private const val urlServer = "http://192.168.0.8:8080"

    @Provides
    @Singleton
    fun provideHttpClient() : HttpClient {
        return HttpClient(Android){
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 1)
            }
            install(Logging)
            install(HttpCache)
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            expectSuccess = true
            defaultRequest{
                url(urlServer)
            }
        }
    }
}