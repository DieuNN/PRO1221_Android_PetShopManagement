package com.example.pro1221_android_petshopmanagement.domain.use_case.customer

import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class GetCustomers(private val customerRepository: CustomerRepository) {
    operator fun invoke(): Flow<List<Customer>> =
        customerRepository.getCustomers()
}