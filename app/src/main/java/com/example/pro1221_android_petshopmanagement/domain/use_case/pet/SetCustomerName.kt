package com.example.pro1221_android_petshopmanagement.domain.use_case.pet

import com.example.pro1221_android_petshopmanagement.domain.repository.PetRepository

class SetCustomerName(private val petRepository: PetRepository) {
    suspend operator fun invoke(customerName: String, id: Int) =
        petRepository.setCustomerName(customerName = customerName, id = id)
}