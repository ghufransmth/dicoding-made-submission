package com.example.core.data.source

import com.example.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkResourceBound<ResultType, RequestType> {
    private var result: Flow<Resource<out ResultType?>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB()?.first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    loadFromDB()?.let { data -> emitAll(data.map { Resource.Success(it) }) }
                }
                is ApiResponse.Empty -> {
                    loadFromDB()?.let { data -> emitAll(data.map { Resource.Success(it) }) }
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            loadFromDB()?.let { data -> emitAll(data.map { Resource.Success(it) }) }
        }
    }

    protected abstract fun loadFromDB(): Flow<ResultType?>?
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<out ResultType?>> = result
}