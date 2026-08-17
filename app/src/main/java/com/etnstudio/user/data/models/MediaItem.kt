package com.etnstudio.user.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "content_items")
data class MediaItem(
    @PrimaryKey val id: String,
    val name: String,
    val type: ItemType,
    val url: String? = null,
    val thumbnail: String? = null,
    val parentId: String? = null,
    val children: List<String> = emptyList(),
    val isLocked: Boolean = false,
    val lockHash: String? = null,
    val lockExpiry: Instant? = null,
    val remoteFavorite: Boolean = false,
    val localFavorite: Boolean = false,
    val lastModified: Long = System.currentTimeMillis()
)

enum class ItemType {
    FOLDER, VIDEO, UNKNOWN
}
