package com.etnstudio.user.domain.repository

import com.etnstudio.user.data.models.MediaItem
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    suspend fun fetchAndCache(username: String, repo: String, branch: String, path: String)
    fun getAllContent(): Flow<List<MediaItem>>
    fun getFolders(): Flow<List<MediaItem>>
    fun getChildren(parentId: String): Flow<List<MediaItem>>
    fun getFavorites(): Flow<List<MediaItem>>
}
