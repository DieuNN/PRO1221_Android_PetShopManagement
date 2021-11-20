package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.pet

import com.example.pro1221_android_petshopmanagement.domain.model.Pet

sealed class PetEvent {
    data class DeletePet(val pet: Pet) : PetEvent()
    data class SetPetSold(val pet: Pet) : PetEvent()
    data class SetUpdateTime(val id:Int, val time:String) : PetEvent()
    data class  RestorePet(val pet: Pet) :PetEvent()
    data class SetCustomerName(val customerName:String, val id: Int) : PetEvent()
}