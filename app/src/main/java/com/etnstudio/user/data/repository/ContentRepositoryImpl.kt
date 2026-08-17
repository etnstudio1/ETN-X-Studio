package com.etnstudio.user.data.repository

import com.etnstudio.user.data.cache.ContentDao
import com.etnstudio.user.data.models.MediaItem
import com.etnstudio.user.data.network.ContentApi
import com.etnstudio.user.domain.repository.ContentRepository
import com.etnstudio.user.utils.JsonNormalizer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val api: ContentApi,
    private val dao: ContentDao,
    private val normalizer: JsonNormalizer
) : ContentRepository {
    override suspend fun fetchAndCache(username: String, repo: String, branch: String, path: String) {
        val raw = api.fetchContent(username, repo, branch, path)
        val items = normalizer.normalize(raw)
        dao.insertAll(items)
    }

    override fun getAllContent(): Flow<List<MediaItem>> = dao.getAll()
    override fun getFolders(): Flow<List<MediaItem>> = dao.getFolders()
    override fun getChildren(parentId: String): Flow<List<MediaItem>> = dao.getChildren(parentId)
    override fun getFavorites(): Flow<List<MediaItem>> = dao.getFavorites()
}
