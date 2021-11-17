package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.animal

import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo

sealed class AnimalInfoEvent {
    data class DeleteAnimalInfo(val animal: AnimalInfo) : AnimalInfoEvent()
}