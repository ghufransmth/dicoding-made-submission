package com.example.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.CreatorUseCase


class FavoriteViewModel (creatorUseCase: CreatorUseCase): ViewModel() {
    val favoriteCreators = creatorUseCase.getFavoriteCreators().asLiveData()
}