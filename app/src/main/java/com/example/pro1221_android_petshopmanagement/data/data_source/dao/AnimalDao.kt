package com.example.pro1221_android_petshopmanagement.data.data_source.dao

import androidx.room.*
import com.example.pro1221_android_petshopmanagement.domain.model.Animal
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {
    @Query("SELECT * FROM table_animal")
    fun getAnimals(): Flow<List<Animal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAnimal(animal: Animal)

    @Update
    suspend fun updateAnimal(animal: Animal)

    @Delete
    suspend fun deleteAnimal(animal: Animal)

    @Query("Select * from table_animal where id = :id")
    suspend fun getAnimalById(id: Int): Animal
}