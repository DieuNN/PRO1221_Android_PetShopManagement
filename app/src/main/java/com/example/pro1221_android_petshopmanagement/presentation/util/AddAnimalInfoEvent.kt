package com.example.pro1221_android_petshopmanagement.presentation.util

import android.graphics.Bitmap

sealed class AddAnimalInfoEvent {
    data class EnteredName(var name:String) : AddAnimalInfoEvent()
    data class EnteredImage(var image:Bitmap?) : AddAnimalInfoEvent()
    data class EnteredKind(var kind:String) : AddAnimalInfoEvent()
    data class EnteredDetail(var detail: String) : AddAnimalInfoEvent()

    object SaveAnimalInfo : AddAnimalInfoEvent()
}