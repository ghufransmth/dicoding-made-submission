package com.example.made_1.Di

import com.example.core.domain.usecase.CreatorUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun creatorUseCase(): CreatorUseCase
}