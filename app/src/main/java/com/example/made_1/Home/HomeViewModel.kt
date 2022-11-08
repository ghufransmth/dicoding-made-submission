package com.example.made_1.Home

import androidx.lifecycle.*
import com.example.core.domain.usecase.CreatorUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(creatorUseCase: CreatorUseCaseImpl): ViewModel() {

    val creator = creatorUseCase.getCreators().asLiveData()

}