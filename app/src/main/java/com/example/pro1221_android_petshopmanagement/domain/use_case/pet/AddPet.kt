package com.example.pro1221_android_petshopmanagement.domain.use_case.pet

import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.domain.repository.PetRepository

class AddPet(private val petRepository: PetRepository) {
    suspend operator fun invoke(pet: Pet) = petRepository.addPet(pet = pet)
}