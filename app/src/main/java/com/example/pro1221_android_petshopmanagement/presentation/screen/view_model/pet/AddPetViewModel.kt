package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.domain.use_case.PetUseCases
import com.example.pro1221_android_petshopmanagement.presentation.util.AddPetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPetViewModel @Inject constructor(
    private val petUseCases: PetUseCases
) : ViewModel() {
    private var _name = mutableStateOf("")
    var name: State<String> = _name

    private val _kind = mutableStateOf("")
    var kind: State<String> = _kind

    private val _price = mutableStateOf(0)
    var price: State<Int> = _price

    private val _detail = mutableStateOf("")
    var detail: State<String> = _detail

    suspend fun onEvent(event: AddPetEvent) {
        when (event) {
            is AddPetEvent.EnteredName -> {
                _name.value = event.name
            }

            is AddPetEvent.EnteredDetail -> {
                _detail.value = event.detail
            }

            is AddPetEvent.EnteredPrice -> {
                _price.value = event.price
            }

            is AddPetEvent.EnteredKind -> {
                _kind.value = event.kind
            }
            is AddPetEvent.SavePet -> {
                viewModelScope.launch {
                    petUseCases.addPet(
                        Pet(
                            detail = detail.value,
                            id = null,
                            image = null,
                            isSold = false,
                            kind = kind.value,
                            name = name.value,
                            price = price.value,
                            updateTime = System.currentTimeMillis().toString()
                        )
                    )
                }
            }
        }
    }
}