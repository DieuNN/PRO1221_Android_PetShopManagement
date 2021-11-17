package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.animal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.use_case.AnimalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalInfoViewModel @Inject constructor(
    private val animalUseCases: AnimalUseCases
) : ViewModel() {

    private val _animalState = mutableStateOf(emptyList<AnimalInfo>())

    val animalState: State<List<AnimalInfo>> = _animalState

    private var job: Job? = null
    private var deletedAnimal: AnimalInfo? = null

    init {
        getAnimals()
    }

    fun onEvent(infoEvent: AnimalInfoEvent) {
        when (infoEvent) {
            is AnimalInfoEvent.DeleteAnimalInfo -> {
                viewModelScope.launch {
                    animalUseCases.deleteAnimal(infoEvent.animal)
                    deletedAnimal = infoEvent.animal
                }
            }
        }
    }

    private fun getAnimals() {
        job?.cancel()

        job = animalUseCases.getAnimals().onEach {
            _animalState.value = it
        }.launchIn(viewModelScope)
    }

}