package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet

import android.os.SystemClock
import android.util.Log
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

    private var _customerName = mutableStateOf("")
    var customerName:State<String> = _customerName

    private var job: Job? = null

    init {
        getPets()
    }

   suspend fun onEvent(event: PetEvent) {
        when (event) {
            is PetEvent.DeletePet -> {
                viewModelScope.launch {
                    petUseCases.deletePet(event.pet)
                }
            }
            is PetEvent.SetPetSold -> {
                viewModelScope.launch {
                    event.pet.id?.let { petUseCases.soldPet (it) }
                }
            }
            is PetEvent.SetUpdateTime -> {
                viewModelScope.launch {
                    event.id.let { petUseCases.updatePetTime(it, System.currentTimeMillis().toString()) }
                }
            }
            is PetEvent.RestorePet -> {
                viewModelScope.launch {
                    petUseCases.addPet(event.pet)
                }
            }
            is PetEvent.SetCustomerName -> {
                _customerName.value = event.customerName
                viewModelScope.launch {
                    event.id.let { petUseCases.setCustomerName(customerName = customerName.value, id = it) }
                }
            }
        }
    }

    private fun getPets() {
        job?.cancel()

        job = petUseCases.getPets().onEach {
            _petState.value = it
        }.launchIn(viewModelScope)
    }
}