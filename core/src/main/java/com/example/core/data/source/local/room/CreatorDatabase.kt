package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.CreatorEntity

@Database(entities = [CreatorEntity::class], version = 2, exportSchema = false)
abstract class CreatorDatabase : RoomDatabase() {
    abstract fun creatorDao(): CreatorDao
}