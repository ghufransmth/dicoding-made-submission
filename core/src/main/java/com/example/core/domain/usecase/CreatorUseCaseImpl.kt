package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.Creator
import com.example.core.domain.repository.CreatorRepository
import javax.inject.Inject

class CreatorUseCaseImpl @Inject constructor(private val creatorRepository: CreatorRepository): CreatorUseCase {
    override fun getCreators() = creatorRepository.getCreators()
    override fun getFavoriteCreators() = creatorRepository.getFavoriteCreators()
    override fun getDetailCreator(id: Int) = creatorRepository.getDetailCreator(id)
    override fun getDetailCreatorRemote(id: Int) = creatorRepository.getDetailCreatorRemote(id)
    override fun getDetailState(id: Int) = creatorRepository.getDetailState(id)
    override fun setFavoriteCreator(creator: Creator) = creatorRepository.setFavoriteCreator(creator)
    override suspend fun searchCreators(query: String): Resource<List<Creator>> = creatorRepository.searchCreators(query)
    override suspend fun insertCreator(creator: Creator) = creatorRepository.insertCreator(creator)
    override suspend fun deleteCreator(creator: Creator): Int = creatorRepository.deleteCreator(creator)
}