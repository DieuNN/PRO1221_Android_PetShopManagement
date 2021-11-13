package com.example.pro1221_android_petshopmanagement.domain.use_case

import com.example.pro1221_android_petshopmanagement.domain.use_case.pet.*

data class PetUseCases(
    var addPet: AddPet,
    var deletePet: DeletePet,
    var getPetById: GetPetById,
    var getPets: GetPets,
    var updatePet: UpdatePet,
    var soldPet: SetPetSold
)
