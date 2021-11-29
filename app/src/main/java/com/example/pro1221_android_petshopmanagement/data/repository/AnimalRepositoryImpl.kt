package com.example.pro1221_android_petshopmanagement.data.repository

import com.example.pro1221_android_petshopmanagement.data.data_source.dao.AnimalDao
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import com.example.pro1221_android_petshopmanagement.domain.repository.AnimalRepository
import kotlinx.coroutines.flow.Flow

class AnimalRepositoryImpl(private val animalDao: AnimalDao) : AnimalRepository {
    override fun getAnimals(): Flow<List<AnimalInfo>> {
        return animalDao.getAnimalsAsFlow()
    }

    override suspend fun addAnimal(animal: AnimalInfo) {
        animalDao.addAnimal(animal = animal)
    }

    override suspend fun updateAnimal(animal: AnimalInfo) {
        animalDao.updateAnimal(animal = animal)
    }

    override suspend fun deleteAnimal(animal: AnimalInfo) {
        animalDao.deleteAnimal(animal = animal)
    }

    override suspend fun getAnimalById(id: Int):AnimalInfo {
        return animalDao.getAnimalById(id = id)
    }
}