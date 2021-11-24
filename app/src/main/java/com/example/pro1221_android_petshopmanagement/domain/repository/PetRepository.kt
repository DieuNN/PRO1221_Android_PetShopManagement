package com.example.pro1221_android_petshopmanagement.domain.repository

import android.graphics.Bitmap
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository  {
    fun getPets() : Flow<List<Pet>>

    suspend fun addPet(pet: Pet)

    suspend fun updatePet(id: Int, petName:String, petImage:Bitmap,petPrice:Int , petKind:String, petDetail:String)

    suspend fun deletePet(pet: Pet)

    suspend fun getPetById(id:Int)

    suspend fun soldPet(id: Int)

    suspend fun updatePetUpdateTime(id: Int, updateTime: String)

    suspend fun restorePet(pet: Pet)

    suspend fun setCustomerName(customerName:String, id: Int)

}