package com.example.coffego.di

import android.util.Log
import com.example.coffego.data.storage.user_storage.User
import com.example.coffego.data.storage.user_storage.UserStorage
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
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    private const val urlServer = "https://server.krea-company.keenetic.pro"

    @Provides
    @Singleton
    fun provideHttpClient(
        userStorage: UserStorage,
    ) : HttpClient {

        val client = HttpClient(Android){
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 10)
                exponentialDelay()
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
                headers {
                    userStorage.getToken()?.let { append("Authorization", "Bearer $it") }
                }
            }
        }
        client.plugin(HttpSend).intercept { request ->
            val originalCall = execute(request)
            if (originalCall.response.status.value == 401) {
                userStorage.saveUser(User())
                originalCall
            } else {
                originalCall
            }
        }
        return client
    }
}