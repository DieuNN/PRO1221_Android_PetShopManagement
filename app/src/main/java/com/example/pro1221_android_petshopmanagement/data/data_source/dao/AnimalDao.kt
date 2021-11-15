package com.example.pro1221_android_petshopmanagement.data.data_source.dao

import androidx.room.*
import com.example.pro1221_android_petshopmanagement.domain.model.AnimalInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {
    @Query("SELECT * FROM table_animal")
    fun getAnimals(): Flow<List<AnimalInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAnimal(animal: AnimalInfo)

    @Update
    suspend fun updateAnimal(animal: AnimalInfo)

    @Delete
    suspend fun deleteAnimal(animal: AnimalInfo)

    @Query("Select * from table_animal where id = :id")
    suspend fun getAnimalById(id: Int): AnimalInfo
}