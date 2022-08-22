package com.example.made_1.Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Creator
import com.example.core.domain.usecase.CreatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val creatorUseCase: CreatorUseCase): ViewModel() {
    fun getDetailCreator(id: Int) = creatorUseCase.getDetailCreatorRemote(id).asLiveData()
    fun setFavoriteCreator(creator: Creator) = creatorUseCase.setFavoriteCreator(creator)
    fun getDetailState(id: Int) = creatorUseCase.getDetailState(id)?.asLiveData()
    fun insertFavorite(creator: Creator) = viewModelScope.launch {
        creatorUseCase.insertCreator(creator)
    }

    fun deleteFavorite(creator: Creator) = viewModelScope.launch {
        creatorUseCase.deleteCreator(creator)
    }
}