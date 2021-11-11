package com.example.pro1221_android_petshopmanagement.data.repository

import com.example.pro1221_android_petshopmanagement.data.data_source.dao.PetDao
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.domain.repository.PetRepository
import kotlinx.coroutines.flow.Flow

class PetRepositoryImpl(private val petDao: PetDao) : PetRepository {
    override fun getPets(): Flow<List<Pet>> {
        return petDao.getPets()
    }

    override suspend fun addPet(pet: Pet) {
        petDao.addPet(pet = pet)
    }

    override suspend fun updatePet(pet: Pet) {
        petDao.updatePet(pet = pet)
    }

    override suspend fun deletePet(pet: Pet) {
        petDao.deletePet(pet = pet)
    }

    override suspend fun getPetById(id: Int) {
        petDao.getPetById(id = id)
    }

    override suspend fun soldPet(id: Int) {
        petDao.soldPet(id = id)
    }
}