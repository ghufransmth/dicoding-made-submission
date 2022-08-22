package com.example.made_1.Di

import com.example.core.domain.usecase.CreatorUseCase
import com.example.core.domain.usecase.CreatorUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideCreatorUseCase(creatorRepository: CreatorUseCaseImpl): CreatorUseCase

}