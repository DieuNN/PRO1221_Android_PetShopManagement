package com.example.pro1221_android_petshopmanagement.ui.model

import android.graphics.Bitmap

data class AnimalInfo(
    var id: Int = 0,
    var name: String = "",
    var kind:String = "",
    var image: Bitmap? = null,
    var detail: String = "",
    var updateTime: String = ""
)
