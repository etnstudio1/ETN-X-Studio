package com.etnstudio.user.data.network

import retrofit2.http.GET

interface RegistryApi {
    @GET("etnstudio/ETN-X-Studio/main/registry.json")
    suspend fun fetchRegistry(): RegistryResponse
}

data class RegistryResponse(
    val username: String? = null,
    val repo: String? = null,
    val branch: String? = "main",
    val path: String? = null,
    val version: Int? = 1
)
