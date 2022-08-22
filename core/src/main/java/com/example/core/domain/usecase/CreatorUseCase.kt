package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.Creator
import kotlinx.coroutines.flow.Flow

interface CreatorUseCase {
    fun getCreators(): Flow<Resource<List<Creator>>>
    fun getFavoriteCreators(): Flow<List<Creator>>
    fun getDetailCreator(id: Int): Flow<Resource<Creator>>
    fun getDetailCreatorRemote(id: Int): Flow<Resource<Creator>>
    fun getDetailState(id: Int): Flow<Creator>?
    fun setFavoriteCreator(creator: Creator)
    suspend fun searchCreators(query: String): Resource<List<Creator>>
    suspend fun insertCreator(creator: Creator)
    suspend fun deleteCreator(creator: Creator): Int
}