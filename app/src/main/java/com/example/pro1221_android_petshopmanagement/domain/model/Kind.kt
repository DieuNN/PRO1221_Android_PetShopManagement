package com.example.pro1221_android_petshopmanagement.ui.model

import android.graphics.Bitmap

data class Kind(
    var id:Int = 0,
    var name:String = "",
    var description:String = "",
    var image:Bitmap? = null
)
