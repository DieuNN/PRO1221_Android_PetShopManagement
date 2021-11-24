package com.example.pro1221_android_petshopmanagement.data.repository

import android.graphics.Bitmap
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

    override suspend fun updatePet(
        id: Int,
        petName: String,
        petImage: Bitmap,
        petPrice: Int,
        petKind: String,
        petDetail: String
    ) {
        petDao.updatePet(id, petName, petImage, petKind = petKind, petDetail = petDetail, petPrice = petPrice)
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

    override suspend fun updatePetUpdateTime(id: Int, updateTime: String) {
        petDao.updatePetUpdateTime(id = id, updateTime = updateTime)
    }

    override suspend fun restorePet(pet: Pet) {
        petDao.restorePet(pet = pet)
    }

    override suspend fun setCustomerName(customerName: String, id: Int) {
        petDao.setCustomerName(customerName = customerName, id = id)
    }
}