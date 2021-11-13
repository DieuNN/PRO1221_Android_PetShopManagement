package com.example.pro1221_android_petshopmanagement.domain.use_case.animal

import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.repository.AnimalRepository

class AddAnimal(
    private val animalRepository: AnimalRepository
) {
    suspend operator fun invoke(animal: AnimalInfo) = animalRepository.addAnimal(animal = animal)
}