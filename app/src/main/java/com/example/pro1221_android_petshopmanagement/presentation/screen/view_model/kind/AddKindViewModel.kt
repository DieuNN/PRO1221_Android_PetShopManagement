package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.use_case.KindUseCase
import com.example.pro1221_android_petshopmanagement.presentation.util.AddKindEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddKindViewModel @Inject constructor(
    private val kindUseCase: KindUseCase
) : ViewModel() {
    private var _name = mutableStateOf("")
    var name: State<String> = _name

    private val _detail = mutableStateOf("")
    var detail: State<String> = _detail

    private var _image: MutableState<Bitmap?> = mutableStateOf(null)
    var image: State<Bitmap?> = _image

    suspend fun onEvent(event: AddKindEvent) {
        when (event) {
            is AddKindEvent.EnteredName -> {
                _name.value = event.name
            }
            is AddKindEvent.EnteredDetail -> {
                _detail.value = event.description

            }
            is AddKindEvent.EnteredImage -> {
                _image.value = event.image
            }
            is AddKindEvent.SaveKind -> {
                kindUseCase.addKind(
                    Kind(
                        id = null,
                        name = name.value,
                        image = image.value,
                        description = detail.value
                    )
                )
            }
        }
    }
}