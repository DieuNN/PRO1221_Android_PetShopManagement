package com.example.pro1221_android_petshopmanagement.presentation.util

import android.graphics.Bitmap

sealed class AddKindEvent {
    data class EnteredName(val name:String) : AddKindEvent()
    data class EnteredDetail(val description:String) : AddKindEvent()
    data class EnteredImage(val image:Bitmap?) : AddKindEvent()
    object SaveKind : AddKindEvent()
}