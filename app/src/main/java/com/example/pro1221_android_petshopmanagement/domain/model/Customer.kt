package com.example.pro1221_android_petshopmanagement.ui.model

import android.graphics.Bitmap

data class Customer(
    var id: Int = 0,
    var name: String = "",
    var address:String = "",
    var phoneNumber:String = "",
    var image: Bitmap? = null
)
