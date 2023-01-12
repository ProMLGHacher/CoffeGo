package com.example.coffego.data.network.catalog

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class CatalogNetwork @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getAll() : Map<String, List<Product>> {
        return httpClient.get("/catalog/all").body()
    }
}