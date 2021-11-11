package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.animal

import com.example.pro1221_android_petshopmanagement.domain.model.Animal

sealed class AnimalEvent {
    data class DeleteAnimal(val animal: Animal) : AnimalEvent()
    object RestoreAnimal : AnimalEvent()
}