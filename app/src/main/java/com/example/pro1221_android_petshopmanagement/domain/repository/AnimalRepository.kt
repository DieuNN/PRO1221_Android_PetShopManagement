package com.example.pro1221_android_petshopmanagement.domain.repository

import com.example.pro1221_android_petshopmanagement.domain.model.Animal
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    fun getAnimals(): Flow<List<Animal>>

    suspend fun addAnimal(animal: Animal)

    suspend fun updateAnimal(animal: Animal)

    suspend fun deleteAnimal(animal: Animal)

    suspend fun getAnimalById(id: Int):Animal
}