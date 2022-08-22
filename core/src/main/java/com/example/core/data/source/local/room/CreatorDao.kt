package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.CreatorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreatorDao {

    @Query("SELECT * FROM tb_creator")
    fun getAllCreator(): Flow<List<CreatorEntity>>

    @Query("SELECT * FROM tb_creator WHERE isFavorite = 1")
    fun getFavoriteCreator(): Flow<List<CreatorEntity>>

    @Query("SELECT * FROM tb_creator WHERE id = :id")
    fun getCreatorById(id: Int): Flow<CreatorEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCreators(creator: List<CreatorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreator(creator: CreatorEntity)

    @Update
    fun updateCreator(creator: CreatorEntity)

    @Delete
    suspend fun deleteCreator(creator: CreatorEntity): Int

}