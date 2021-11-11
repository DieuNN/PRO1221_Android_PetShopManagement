package com.example.pro1221_android_petshopmanagement.domain.use_case.pet

import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.domain.repository.PetRepository

class DeletePet(private val petRepository: PetRepository) {
    suspend operator fun invoke(pet:Pet) = petRepository.deletePet(pet = pet)
}