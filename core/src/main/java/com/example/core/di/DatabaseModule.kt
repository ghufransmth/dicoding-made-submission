package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.source.local.room.CreatorDao
import com.example.core.data.source.local.room.CreatorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CreatorDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASSPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(context, CreatorDatabase::class.java, BuildConfig.DB)
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideCreatorDao(database: CreatorDatabase): CreatorDao = database.creatorDao()
}