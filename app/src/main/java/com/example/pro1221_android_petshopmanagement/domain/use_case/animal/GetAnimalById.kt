package com.example.pro1221_android_petshopmanagement.domain.use_case.animal

import com.example.pro1221_android_petshopmanagement.domain.repository.AnimalRepository

class GetAnimalById(
    private val animalRepository: AnimalRepository
) {
    suspend operator fun invoke(id: Int) = animalRepository.getAnimalById(id = id)
}