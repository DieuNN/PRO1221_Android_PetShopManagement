package com.example.pro1221_android_petshopmanagement.presentation.util

sealed class AddPetEvent {
    data class EnteredName(val name:String) : AddPetEvent()
    data class EnteredKind(val kind:String) : AddPetEvent()
    data class EnteredPrice(val price:Int) : AddPetEvent()
    data class EnteredDetail(val detail:String) : AddPetEvent()

    object SavePet:AddPetEvent()
}