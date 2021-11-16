package com.example.pro1221_android_petshopmanagement.presentation.util

import android.graphics.Bitmap

sealed class AddPetEvent {
    data class EnteredName(val name: String) : AddPetEvent()
    data class EnteredKind(val kind: String) : AddPetEvent()
    data class EnteredPrice(val price: Int) : AddPetEvent()
    data class EnteredDetail(val detail: String) : AddPetEvent()
    data class EnteredImage(val image: Bitmap?) : AddPetEvent()

    object SavePet : AddPetEvent()
}