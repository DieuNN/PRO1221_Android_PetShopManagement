package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.domain.use_case.PetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val petUseCases: PetUseCases
) : ViewModel() {
    private val _petState = mutableStateOf(emptyList<Pet>())
    val petState: State<List<Pet>> = _petState

    private var job: Job? = null
    private var deletedPet: Pet? = null

    init {
        getPets()
    }

    fun onEvent(event: PetEvent) {
        when (event) {
            is PetEvent.DeletePet -> {
                viewModelScope.launch {
                    petUseCases.deletePet(event.pet)
                    deletedPet = event.pet
                }
            }
            is PetEvent.SetPetSold -> {
                viewModelScope.launch {
                    event.pet.id?.let { petUseCases.soldPet (it) }
                }
            }
            else -> {
                viewModelScope.launch {
                    petUseCases.addPet(deletedPet!!)
                }
            }
        }
    }

    fun getPets() {
        job?.cancel()

        job = petUseCases.getPets().onEach {
            _petState.value = it
        }.launchIn(viewModelScope)
    }
}