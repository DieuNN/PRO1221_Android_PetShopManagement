package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.domain.use_case.PetUseCases
import com.example.pro1221_android_petshopmanagement.presentation.util.EditPetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditPetViewModel @Inject constructor(
    private val petUseCases: PetUseCases,

    ) : ViewModel() {
    private val _name = mutableStateOf("")
    var name: State<String> = _name

    private val _kind = mutableStateOf("")
    var kind: State<String> = _kind

    private val _image: MutableState<Bitmap?> = mutableStateOf(null)
    var image: State<Bitmap?> = _image

    private val _price = mutableStateOf(0)
    var price: State<Int> = _price

    private val _detail = mutableStateOf("")
    var detail: State<String> = _detail

    private val _id = mutableStateOf(0)
    var id:State<Int> = _id

    fun setDefaultValue(pet: Pet) {
        _name.value = pet.name
        _image.value = pet.image
        _price.value = pet.price
        _kind.value = pet.kind
        _detail.value = pet.detail
    }


    suspend fun onEvent(event: EditPetEvent) {
        when (event) {
            is EditPetEvent.EnteredDetail -> {
                _detail.value = event.detail
            }
            is EditPetEvent.EnteredImage -> {
                _image.value = event.image
            }
            is EditPetEvent.EnteredKind -> {
                _kind.value = event.kind
            }
            is EditPetEvent.EnteredName -> {
                _name.value = event.name
            }
            is EditPetEvent.EnteredPrice -> {
                _price.value = event.price
            }
            EditPetEvent.SaveChange -> {
                petUseCases.updatePet(
                    id = id.value,
                    kind = kind.value,
                    detail = detail.value,
                    name = name.value,
                    image = image.value!!,
                    price = price.value
                )
            }
            is EditPetEvent.EnteredId -> {
                _id.value = event.id
            }
        }
    }
}