package com.etnstudio.user.domain.repository

interface RegistryRepository {
    suspend fun fetchRegistry(): RegistryConfig
    data class RegistryConfig(val username: String, val repo: String, val branch: String, val path: String)
}
