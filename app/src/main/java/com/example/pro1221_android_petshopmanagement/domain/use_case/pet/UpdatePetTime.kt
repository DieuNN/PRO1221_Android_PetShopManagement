package com.example.pro1221_android_petshopmanagement.domain.use_case.pet

import com.example.pro1221_android_petshopmanagement.domain.repository.PetRepository

class UpdatePetTime(private val petRepository: PetRepository) {
    suspend operator fun invoke(id: Int, updateTime: String) =
        petRepository.updatePetUpdateTime(id, updateTime)
}