package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.animal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro1221_android_petshopmanagement.domain.model.Animal
import com.example.pro1221_android_petshopmanagement.domain.use_case.animal.AnimalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val animalUseCases: AnimalUseCases
) : ViewModel() {

    private val _animalState = mutableStateOf(emptyList<Animal>())

    val animalState: State<List<Animal>> = _animalState

    private var job: Job? = null
    private var deletedAnimal: Animal? = null

    fun onEvent(event: AnimalEvent) {
        if (event is AnimalEvent.DeleteAnimal) {
            viewModelScope.launch {
                animalUseCases.deleteAnimal(event.animal)
                deletedAnimal = event.animal
            }
        } else {
            viewModelScope.launch {
                animalUseCases.addAnimal(deletedAnimal!!)
            }

        }
    }

    private fun getAnimals() {
        job?.cancel()

        job = viewModelScope.launch {
            animalUseCases.getAnimals().onEach {
                _animalState.value = it
            }
        }
    }

}