    package com.example.pro1221_android_petshopmanagement.domain.use_case

import com.example.pro1221_android_petshopmanagement.domain.use_case.animal.*

data class AnimalUseCases(
    var addAnimal: AddAnimal,
    var updateAnimal: UpdateAnimal,
    var deleteAnimal: DeleteAnimal,
    var getAnimalById: GetAnimalById,
    var getAnimals: GetAnimals
)
