package com.etnstudio.user.data.repository

import com.etnstudio.user.data.network.RegistryApi
import com.etnstudio.user.domain.repository.RegistryRepository
import javax.inject.Inject

class RegistryRepositoryImpl @Inject constructor(
    private val api: RegistryApi
) : RegistryRepository {
    override suspend fun fetchRegistry(): RegistryRepository.RegistryConfig {
        val response = api.fetchRegistry()
        return RegistryRepository.RegistryConfig(
            username = response.username ?: "etnstudio",
            repo = response.repo ?: "ETN-X-Studio",
            branch = response.branch ?: "main",
            path = response.path ?: "data/data.json"
        )
    }
}
