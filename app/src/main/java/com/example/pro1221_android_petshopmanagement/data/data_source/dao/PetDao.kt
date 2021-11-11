package com.example.pro1221_android_petshopmanagement.data.data_source.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Query("SELECT * FROM table_pet")
    fun getPets():Flow<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPet(pet: Pet)

    @Update
    suspend fun updatePet(pet: Pet)

    @Delete
    suspend fun deletePet(pet: Pet)

    @Query("select * from table_pet where id = :id")
    suspend fun getPetById(id:Int) : Pet


}