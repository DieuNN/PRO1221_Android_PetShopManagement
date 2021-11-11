package com.example.pro1221_android_petshopmanagement.domain.use_case.animal

import com.example.pro1221_android_petshopmanagement.domain.model.Animal
import com.example.pro1221_android_petshopmanagement.domain.repository.AnimalRepository

class DeleteAnimal(
    private val animalRepository: AnimalRepository
) {
    suspend operator fun invoke(animal: Animal) = animalRepository.deleteAnimal(animal = animal)
}