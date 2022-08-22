package com.example.core.data.source.local

import com.example.core.data.source.local.entity.CreatorEntity
import com.example.core.data.source.local.room.CreatorDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val creatorDao: CreatorDao) {
    fun getAllCreators(): Flow<List<CreatorEntity>> = creatorDao.getAllCreator()

    fun getFavoriteCreators(): Flow<List<CreatorEntity>> = creatorDao.getFavoriteCreator()

    fun getCreatorById(id: Int): Flow<CreatorEntity>? = creatorDao.getCreatorById(id)

    suspend fun insertAllCreators(creators: List<CreatorEntity>) = creatorDao.insertAllCreators(creators)

    suspend fun insertCreator(creator: CreatorEntity) = creatorDao.insertCreator(creator)

    suspend fun deleteCreator(creator: CreatorEntity) = creatorDao.deleteCreator(creator)

    fun editCreator(creator: CreatorEntity) = creatorDao.updateCreator(creator)

}