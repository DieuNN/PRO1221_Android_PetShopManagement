package com.example.pro1221_android_petshopmanagement.domain.use_case.customer

import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.repository.CustomerRepository

class DeleteCustomer(private val customerRepository: CustomerRepository) {
    suspend operator fun invoke(customer: Customer) =
        customerRepository.deleteCustomer(customer = customer)
}