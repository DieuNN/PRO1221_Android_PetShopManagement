package com.example.pro1221_android_petshopmanagement.domain.use_case

import com.example.pro1221_android_petshopmanagement.domain.use_case.customer.AddCustomer
import com.example.pro1221_android_petshopmanagement.domain.use_case.customer.DeleteCustomer
import com.example.pro1221_android_petshopmanagement.domain.use_case.customer.GetCustomer
import com.example.pro1221_android_petshopmanagement.domain.use_case.customer.GetCustomers

data class CustomerUseCase(
    var addCustomer: AddCustomer,
    var getCustomer: GetCustomer,
    var deleteCustomer: DeleteCustomer,
    var getCustomers: GetCustomers
)
