package com.example.pro1221_android_petshopmanagement.domain.repository

import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    fun getAnimals(): Flow<List<AnimalInfo>>

    suspend fun addAnimal(animal: AnimalInfo)

    suspend fun updateAnimal(animal: AnimalInfo)

    suspend fun deleteAnimal(animal: AnimalInfo)

    suspend fun getAnimalById(id: Int):AnimalInfo
}