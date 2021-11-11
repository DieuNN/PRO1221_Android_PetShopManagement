package com.example.pro1221_android_petshopmanagement.domain.use_case.pet

import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.domain.repository.PetRepository
import kotlinx.coroutines.flow.Flow

class GetPets(private val petRepository: PetRepository) {
    operator fun invoke(): Flow<List<Pet>> = petRepository.getPets()
}