package com.example.pro1221_android_petshopmanagement.ui.model

import android.graphics.Bitmap

data class Pet(
    var id:Int = 0,
    var name:String = "",
    var kind:String = "",
    var detail:String = "",
    var isSold:Boolean = false,
    var updateTime:String = "",
    var image:Bitmap? = null,
    var price:Int = 0
)
