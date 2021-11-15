package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer

import com.example.pro1221_android_petshopmanagement.domain.model.Customer

sealed class CustomerEvent {
    data class DeleteCustomer(val customer:Customer) : CustomerEvent()
    data class RestoreCustomer(val customer: Customer) : CustomerEvent()
}