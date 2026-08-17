package com.etnstudio.user.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.etnstudio.user.data.models.MediaItem

@Database(entities = [MediaItem::class, HistoryEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
    abstract fun historyDao(): HistoryDao
}
