package com.example.pro1221_android_petshopmanagement.presentation.util

import android.graphics.Bitmap

sealed class EditPetEvent {
    data class EnteredName(val name:String) : EditPetEvent()
    data class EnteredImage(val image: Bitmap?):EditPetEvent()
    data class EnteredKind(val kind:String) : EditPetEvent()
    data class EnteredPrice(val price:Int) : EditPetEvent()
    data class EnteredDetail(val detail:String) : EditPetEvent()
    data class EnteredId(val id:Int) : EditPetEvent()
    object SaveChange : EditPetEvent()
}