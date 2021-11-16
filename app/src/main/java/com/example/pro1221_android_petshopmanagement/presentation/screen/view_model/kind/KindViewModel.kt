package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.kind

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.use_case.KindUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KindViewModel @Inject constructor(
    private val kindUseCase: KindUseCase
) : ViewModel() {
    private val _kindState = mutableStateOf(emptyList<Kind>())
    val kindState: State<List<Kind>> = _kindState

    private var job: Job? = null

    init {
        getKinds()
    }

    fun onEvent(event: KindEvent) {
        when (event) {
            is KindEvent.DeleteKind -> {
                viewModelScope.launch {
                    kindUseCase.deleteKind(event.kind)
                }
            }
            is KindEvent.RestoreKind -> {
                viewModelScope.launch {
                    kindUseCase.addKind(event.kind)
                }
            }
        }
    }

    fun getKinds() {
        job?.cancel()

        job = kindUseCase.getKinds().onEach {
            _kindState.value = it
        }.launchIn(viewModelScope)
    }
}