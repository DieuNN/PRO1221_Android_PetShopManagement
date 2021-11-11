package com.example.pro1221_android_petshopmanagement.domain.use_case.animal

import com.example.pro1221_android_petshopmanagement.domain.repository.AnimalRepository

class GetAnimals(
    private val animalRepository: AnimalRepository
) {
    operator fun invoke() = animalRepository.getAnimals()
}