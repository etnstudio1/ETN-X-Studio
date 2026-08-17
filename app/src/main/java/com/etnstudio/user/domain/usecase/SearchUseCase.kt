package com.etnstudio.user.domain.usecase

import com.etnstudio.user.data.models.MediaItem
import com.etnstudio.user.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repo: ContentRepository
) {
    fun search(query: String): Flow<List<MediaItem>> {
        return repo.getAllContent().map { items ->
            if (query.isBlank()) items
            else items.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}
