package com.example.pro1221_android_petshopmanagement.data.repository

import com.example.pro1221_android_petshopmanagement.data.data_source.dao.AnimalDao
import com.example.pro1221_android_petshopmanagement.domain.model.Animal
import com.example.pro1221_android_petshopmanagement.domain.repository.AnimalRepository
import kotlinx.coroutines.flow.Flow

class AnimalRepositoryImpl(private val animalDao: AnimalDao) : AnimalRepository {
    override fun getAnimals(): Flow<List<Animal>> {
        return animalDao.getAnimals()
    }

    override suspend fun addAnimal(animal: Animal) {
        animalDao.addAnimal(animal = animal)
    }

    override suspend fun updateAnimal(animal: Animal) {
        animalDao.updateAnimal(animal = animal)
    }

    override suspend fun deleteAnimal(animal: Animal) {
        animalDao.deleteAnimal(animal = animal)
    }

    override suspend fun getAnimalById(id: Int):Animal {
        return animalDao.getAnimalById(id = id)
    }
}