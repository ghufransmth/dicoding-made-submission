package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.CreatorResponse
import com.example.core.data.source.remote.response.CreatorsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("creators?key=5a62a3d94e2e45c5bd11c2314966f57c")
    suspend fun getCreator(): CreatorsResponse

    @GET("creators/{creatorId}?key=5a62a3d94e2e45c5bd11c2314966f57c")
    suspend fun getDetailCreator(@Path("creatorId") creatorId: String): CreatorResponse

    @GET("creators?key=5a62a3d94e2e45c5bd11c2314966f57c")
    suspend fun searchCreators(@Query("search") search: String): CreatorsResponse
}