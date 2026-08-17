package com.etnstudio.user.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Entity
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "history")
data class HistoryEntry(
    @PrimaryKey val mediaId: String,
    val watchedAt: Long,
    val title: String,
    val thumbnail: String?
)

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: HistoryEntry)

    @Query("SELECT * FROM history ORDER BY watchedAt DESC LIMIT 50")
    fun getAll(): Flow<List<HistoryEntry>>

    @Query("DELETE FROM history")
    suspend fun clearAll()
}
