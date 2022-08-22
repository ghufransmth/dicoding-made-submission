package com.example.core.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import com.airbnb.lottie.BuildConfig
import com.example.core.data.source.local.room.CreatorDao
import com.example.core.data.source.local.room.CreatorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CreatorDatabase = Room.databaseBuilder(
        context,
        CreatorDatabase::class.java, "Creator.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGameDao(database: CreatorDatabase): CreatorDao = database.creatorDao()
}