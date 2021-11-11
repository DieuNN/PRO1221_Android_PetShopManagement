package com.example.pro1221_android_petshopmanagement.domain.repository

import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository  {
    fun getPets() : Flow<List<Pet>>

    suspend fun addPet(pet: Pet)

    suspend fun updatePet(pet: Pet)

    suspend fun deletePet(pet: Pet)

    suspend fun getPetById(id:Int)

    suspend fun soldPet(id: Int)
}