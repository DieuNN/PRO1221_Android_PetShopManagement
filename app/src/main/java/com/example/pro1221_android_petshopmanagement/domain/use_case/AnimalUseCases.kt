    package com.example.pro1221_android_petshopmanagement.domain.use_case

import com.example.pro1221_android_petshopmanagement.domain.use_case.animal.*

data class AnimalUseCases(
    val addAnimal: AddAnimal,
    val updateAnimal: UpdateAnimal,
    val deleteAnimal: DeleteAnimal,
    val getAnimalById: GetAnimalById,
    val getAnimals: GetAnimals
)
