package com.example.core.data.source

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.entity.CreatorEntity
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.CreatorResponse
import com.example.core.domain.model.Creator
import com.example.core.domain.repository.CreatorRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class CreatorRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : CreatorRepository {
    override fun getCreators(): Flow<Resource<List<Creator>>> =
        object : NetworkResourceBound<List<Creator>, List<CreatorResponse>>() {
            override fun loadFromDB(): Flow<List<Creator>> {
                return localDataSource.getAllCreators().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Creator>?): Boolean =
                data?.isEmpty() == true || data == null

            override suspend fun createCall(): Flow<ApiResponse<List<CreatorResponse>>> =
                remoteDataSource.getAllCreators()

            override suspend fun saveCallResult(data: List<CreatorResponse>) {
                val creatorList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertAllCreators(creatorList)
            }
        }.asFlow() as Flow<Resource<List<Creator>>>

    override fun getFavoriteCreators(): Flow<List<Creator>> {
        return localDataSource.getFavoriteCreators().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getDetailState(id: Int): Flow<Creator>? {
        return localDataSource.getCreatorById(id)?.map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun getDetailCreator(id: Int): Flow<Resource<Creator>> =
        object : NetworkResourceBound<Creator, CreatorResponse>() {
            override fun loadFromDB(): Flow<Creator?>? {
                return localDataSource.getCreatorById(id)?.map { creatorEntity: CreatorEntity? ->
                    if (creatorEntity == null) {
                        return@map null
                    } else {
                        return@map DataMapper.mapEntityToDomain(creatorEntity)
                    }
                }
            }

            override fun shouldFetch(data: Creator?): Boolean {
                return data?.name == "" || data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<CreatorResponse>> =
                remoteDataSource.getDetailCreator(id)

            override suspend fun saveCallResult(data: CreatorResponse) {
                val creatorDetail = DataMapper.mapResponseToEntity(data)
                localDataSource.insertCreator(creatorDetail)
            }
        }.asFlow() as Flow<Resource<Creator>>

    override fun getDetailCreatorRemote(id: Int): Flow<Resource<Creator>> {
        return object : NetworkResourceOnly<Creator, CreatorResponse>() {
            override fun loadFromNetwork(data: CreatorResponse): Flow<Creator> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<CreatorResponse>> {
                return remoteDataSource.getDetailCreator(id)
            }

        }.asFlow()
    }

    override fun setFavoriteCreator(creator: Creator) {
        val creatorEntity = DataMapper.mapDomainToEntity(creator)
        creatorEntity.isFavorite = !creatorEntity.isFavorite
        appExecutors.diskIO().execute { localDataSource.editCreator(creatorEntity) }
    }

    override suspend fun searchCreators(query: String): Resource<List<Creator>> {
        return when(val response = remoteDataSource.searchCreator(query).first()) {
            is ApiResponse.Success -> {
                val creatorsEntities = DataMapper.mapResponsesToEntities(response.data)
                val creators = DataMapper.mapEntitiesToDomain(creatorsEntities)
                Resource.Success(creators)
            }
            is ApiResponse.Error -> {
                Resource.Error(response.errorMessage, null)
            }
            is ApiResponse.Empty -> {
                Resource.Error(response.toString(), null)
            }
        }
    }

    override suspend fun insertCreator(creator: Creator) {
        val creatorEntity = DataMapper.mapDomainToEntity(creator)
        appExecutors.diskIO().execute { localDataSource.editCreator(creatorEntity) }
    }

    override suspend fun deleteCreator(creator: Creator): Int {
        val domainUser = DataMapper.mapDomainToEntity(creator)
        return localDataSource.deleteCreator(domainUser)
    }

}