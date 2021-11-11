package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet

import com.example.pro1221_android_petshopmanagement.domain.model.Pet

sealed class PetEvent {
    data class DeletePet(val pet: Pet) : PetEvent()
    object RestorePet :PetEvent()
}