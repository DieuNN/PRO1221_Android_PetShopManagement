package com.example.pro1221_android_petshopmanagement.domain.model

data class Bill(
    var id:Int = 0,
    var petId:Int = 0,
    var customerId:Int = 0,
    var updateTime:String = System.currentTimeMillis().toString(),
)
