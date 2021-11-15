package com.example.pro1221_android_petshopmanagement.domain.repository

import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository  {

    fun getCustomer(): Flow<List<Customer>>

    suspend fun updateCustomer(customer: Customer)

    suspend fun addCustomer(customer: Customer)

    fun getCustomer(id:Int): Customer

    suspend fun deleteCustomer(customer: Customer)

    suspend fun restoreCustomer(customer: Customer)
}