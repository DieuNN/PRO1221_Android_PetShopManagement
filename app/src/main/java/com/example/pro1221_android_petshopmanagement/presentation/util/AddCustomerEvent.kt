package com.example.pro1221_android_petshopmanagement.presentation.util

import android.graphics.Bitmap

sealed class AddCustomerEvent {
    data class EnteredName(val name:String) : AddCustomerEvent()
    data class EnteredAddress(val address: String):AddCustomerEvent()
    data class EnteredPhoneNumber(val phoneNumber:String) : AddCustomerEvent()
    data class EnteredImage(val image: Bitmap?):AddCustomerEvent()

    object SaveCustomer:AddCustomerEvent()
}