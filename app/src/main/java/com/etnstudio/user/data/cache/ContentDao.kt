package com.etnstudio.user.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.etnstudio.user.data.models.MediaItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MediaItem>)

    @Query("SELECT * FROM content_items")
    fun getAll(): Flow<List<MediaItem>>

    @Query("SELECT * FROM content_items WHERE id = :id")
    suspend fun getById(id: String): MediaItem?

    @Query("DELETE FROM content_items")
    suspend fun clearAll()

    @Query("SELECT * FROM content_items WHERE parentId = :parentId")
    fun getChildren(parentId: String): Flow<List<MediaItem>>

    @Query("SELECT * FROM content_items WHERE type = 'FOLDER' ORDER BY name")
    fun getFolders(): Flow<List<MediaItem>>

    @Query("SELECT * FROM content_items WHERE remoteFavorite = 1 OR localFavorite = 1")
    fun getFavorites(): Flow<List<MediaItem>>
}
