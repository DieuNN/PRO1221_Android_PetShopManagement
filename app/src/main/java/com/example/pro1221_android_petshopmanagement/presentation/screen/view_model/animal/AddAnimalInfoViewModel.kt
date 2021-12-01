package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.animal

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.use_case.AnimalUseCases
import com.example.pro1221_android_petshopmanagement.presentation.util.AddAnimalInfoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAnimalInfoViewModel @Inject constructor(
    private val animalUseCases: AnimalUseCases
) : ViewModel() {
    private val _name = mutableStateOf("")
    var name: State<String> = _name

    private var _kind = mutableStateOf("")
    var kind: State<String> = _kind

    private var _image: MutableState<Bitmap?> = mutableStateOf(null)
    var image: State<Bitmap?> = _image

    private var _detail = mutableStateOf("")
    var detail: State<String> = _detail

    suspend fun onEvent(event: AddAnimalInfoEvent) {
        when (event) {
            is AddAnimalInfoEvent.EnteredImage -> {
                _image.value = event.image
            }
            is AddAnimalInfoEvent.EnteredKind -> {
                _kind.value = event.kind
            }
            is AddAnimalInfoEvent.EnteredName -> {
                _name.value = event.name
            }
            is AddAnimalInfoEvent.EnteredDetail -> {
                _detail.value = event.detail
            }
            AddAnimalInfoEvent.SaveAnimalInfo -> {
                viewModelScope.launch {
                    animalUseCases.addAnimal(
                        AnimalInfo(
                            image = image.value,
                            detail = detail.value,
                            kind = kind.value,
                            name = name.value,
                            updateTime = System.currentTimeMillis().toString()
                        )
                    )
                }
            }
        }
    }
}